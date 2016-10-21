package com.quseit.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageDownLoader {
    private static final String ImageDownLoader_Log = Utils
            .makeLogTag(ImageDownLoader.class);

    /** 淇濆瓨姝ｅ湪涓嬭浇鎴栫瓑寰呬笅杞界殑URL鍜岀浉搴斿け璐ヤ笅杞芥鏁帮紙鍒濆涓�0锛夛紝闃叉婊氬姩鏃跺娆′笅杞� */
    private Hashtable<String, Integer> taskCollection;
    /** 缂撳瓨绫� */
    private LruCache<String, Bitmap> lruCache;
    /** 绾跨▼姹� */
    private ExecutorService threadPool;
    /** 缂撳瓨鏂囦欢鐩綍 锛堝鏃燬D鍗★紝鍒檇ata鐩綍涓嬶級 */
    private File cacheFileDir;
    /** 缂撳瓨鏂囦欢澶� */
    private static final String DIR_CACHE = ".diandian_cache";
    /** 缂撳瓨鏂囦欢澶规渶澶у閲忛檺鍒讹紙10M锛� */
    private static final long DIR_CACHE_LIMIT = 10 * 1024 * 1024;
    /** 鍥剧墖涓嬭浇澶辫触閲嶈瘯娆℃暟 */
    private static final int IMAGE_DOWNLOAD_FAIL_TIMES = 2;

    @SuppressLint("NewApi") 
    public ImageDownLoader(Context context) {
        // 鑾峰彇绯荤粺鍒嗛厤缁欐瘡涓簲鐢ㄧ▼搴忕殑鏈�澶у唴瀛�
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        // 缁橪ruCache鍒嗛厤鏈�澶у唴瀛樼殑1/8
        lruCache = new LruCache<String, Bitmap>(maxMemory / 8) {
            // 蹇呴』閲嶅啓姝ゆ柟娉曪紝鏉ユ祴閲廈itmap鐨勫ぇ灏�
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
        taskCollection = new Hashtable<String, Integer>();
        // 鍒涘缓绾跨▼鏁�
        threadPool = Executors.newFixedThreadPool(10);
        cacheFileDir = Utils.createFileDir(context, DIR_CACHE);
    }

    /**
     * 娣诲姞Bitmap鍒板唴瀛樼紦瀛�
     * 
     * @param key
     * @param bitmap
     */
    @SuppressLint("NewApi") private void addLruCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null && bitmap != null) {
            lruCache.put(key, bitmap);
        }
    }

    /**
     * 浠庡唴瀛樼紦瀛樹腑鑾峰彇Bitmap
     * 
     * @param key
     * @return
     */
    @SuppressLint("NewApi") private Bitmap getBitmapFromMemCache(String key) {
        return lruCache.get(key);
    }

    /**
     * 寮傛涓嬭浇鍥剧墖锛屽苟鎸夋寚瀹氬搴﹀拰楂樺害鍘嬬缉鍥剧墖
     * 
     * @param url
     * @param width
     * @param height
     * @param listener
     *            鍥剧墖涓嬭浇瀹屾垚鍚庤皟鐢ㄦ帴鍙�
     */
    public void loadImage(final String url, final int width, final int height,
            AsyncImageLoaderListener listener) {
        Log.i(ImageDownLoader_Log, "loadImage:" + url);
        final ImageHandler handler = new ImageHandler(listener);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i(ImageDownLoader_Log, "loadImage run:" + url);
                Bitmap bitmap = downloadImage(url, width, height);
                Message msg = handler.obtainMessage();
                msg.obj = bitmap;
                handler.sendMessage(msg);
                // 灏咮itmap 鍔犲叆鍐呭瓨缂撳瓨
                addLruCache(url, bitmap);
                // 鍔犲叆鏂囦欢缂撳瓨鍓嶏紝闇�鍒ゆ柇缂撳瓨鐩綍澶у皬鏄惁瓒呰繃闄愬埗锛岃秴杩囧垯娓呯┖缂撳瓨鍐嶅姞鍏�
                long cacheFileSize = Utils.getFileSize(cacheFileDir);
                if (cacheFileSize > DIR_CACHE_LIMIT) {
                    Log.i(ImageDownLoader_Log, cacheFileDir
                            + " size has exceed limit." + cacheFileSize);
                    Utils.delFile(cacheFileDir, false);
                    taskCollection.clear();
                }
                // 缂撳瓨鏂囦欢鍚嶇О锛� 鏇挎崲url涓潪瀛楁瘝鍜岄潪鏁板瓧鐨勫瓧绗︼紝闃叉绯荤粺璇涓烘枃浠惰矾寰勶級
                String urlKey = url.replaceAll("[^\\w]", "");
                // 灏咮itmap鍔犲叆鏂囦欢缂撳瓨
                Utils.savaBitmap(cacheFileDir, urlKey, bitmap);
            }
        };
        // 璁板綍璇rl锛岄槻姝㈡粴鍔ㄦ椂澶氭涓嬭浇锛�0浠ｈ〃璇rl涓嬭浇澶辫触娆℃暟
        taskCollection.put(url, 0);
        threadPool.execute(runnable);
    }

    /**
     * 鑾峰彇Bitmap, 鑻ュ唴瀛樼紦瀛樹负绌猴紝鍒欏幓鏂囦欢缂撳瓨涓幏鍙�
     * 
     * @param url
     * @return 鑻ョ紦瀛樹腑娌℃壘鍒帮紝鍒欒繑鍥瀗ull
     */
    public Bitmap getBitmapCache(String url) {
        // 鍘诲url涓壒娈婂瓧绗︿綔涓烘枃浠剁紦瀛樼殑鍚嶇О
        String urlKey = url.replaceAll("[^\\w]", "");
        if (getBitmapFromMemCache(url) != null) {
            return getBitmapFromMemCache(url);
        } else if (Utils.isFileExists(cacheFileDir, urlKey)
                && Utils.getFileSize(new File(cacheFileDir, urlKey)) > 0) {
            // 浠庢枃浠剁紦瀛樹腑鑾峰彇Bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(cacheFileDir.getPath()
                    + File.separator + urlKey);
            // 灏咮itmap 鍔犲叆鍐呭瓨缂撳瓨
            addLruCache(url, bitmap);
            return bitmap;
        }
        return null;
    }

    /**
     * 涓嬭浇鍥剧墖锛屽苟鎸夋寚瀹氶珮搴﹀拰瀹藉害鍘嬬缉
     * 
     * @param url
     * @param width
     * @param height
     * @return
     */
    private Bitmap downloadImage(String url, int width, int height) {
        Bitmap bitmap = null;
        HttpClient httpClient = new DefaultHttpClient();
        try {
            httpClient.getParams().setParameter(
                    CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = httpResponse.getEntity();
                //瑙ｅ喅缂╂斁澶у浘鏃跺嚭鐜癝kImageDecoder::Factory returned null閿欒
                byte[] byteIn = EntityUtils.toByteArray(entity);
                BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                bmpFactoryOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(byteIn, 0, byteIn.length,
                        bmpFactoryOptions);
                Log.d("ImageDownLoader", "downloadImage:"+bmpFactoryOptions.outHeight+"|"+height+"|"+bmpFactoryOptions.outWidth+"|"+width);
                /*int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
                        / height);
                int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
                        / width);
                if (heightRatio > 1 && widthRatio > 1) {
                    bmpFactoryOptions.inSampleSize = heightRatio > widthRatio ? heightRatio
                            : widthRatio;
                }*/
                bmpFactoryOptions.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeByteArray(byteIn, 0,
                        byteIn.length, bmpFactoryOptions);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null && httpClient.getConnectionManager() != null) {
                httpClient.getConnectionManager().shutdown();
            }
        }
        // 涓嬭浇澶辫触锛屽啀閲嶆柊涓嬭浇
        // 鏈緥鏄浘鐗囦笅杞藉け璐ュ垯鍐嶆涓嬭浇锛屽彲鏍规嵁闇�瑕佹敼鍙橈紝姣斿璁板綍涓嬭浇澶辫触鐨勫浘鐗嘦RL锛屽湪鏌愪釜鏃跺埢鍐嶆涓嬭浇
        if (taskCollection.get(url) != null) {
            int times = taskCollection.get(url);
            if (bitmap == null
                    && times < IMAGE_DOWNLOAD_FAIL_TIMES) {
                times++;
                taskCollection.put(url, times);
                bitmap = downloadImage(url, width, height);
                Log.i(ImageDownLoader_Log, "Re-download " + url + ":" + times);
            }
        }
        return bitmap;
    }

    /**
     * 鍙栨秷姝ｅ湪涓嬭浇鐨勪换鍔�
     */
    public synchronized void cancelTasks() {
        if (threadPool != null) {
            threadPool.shutdownNow();
            threadPool = null;
        }
    }

    /**
     * 鑾峰彇浠诲姟鍒楄〃
     * 
     * @return
     */
    public Hashtable<String, Integer> getTaskCollection() {
        return taskCollection;
    }

    /** 寮傛鍔犺浇鍥剧墖鎺ュ彛 */
    public interface AsyncImageLoaderListener {
        void onImageLoader(Bitmap bitmap);
    }

    /** 寮傛鍔犺浇瀹屾垚鍚庯紝鍥剧墖澶勭悊 */
    static class ImageHandler extends Handler {

        private AsyncImageLoaderListener listener;

        public ImageHandler(AsyncImageLoaderListener listener) {
            this.listener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            listener.onImageLoader((Bitmap) msg.obj);
        }
    }
}

