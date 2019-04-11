package com.huyingbao.core.progress;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by liujunfeng on 2019/4/10.
 */
public interface DownloadApi {
    /**
     * 下载文件
     *
     * @param tag
     * @param url
     * @return
     */
    @GET
    @Streaming
    Observable<ResponseBody> download(
            @Header(ProgressInfo.TAG) String tag,
            @Url String url);
}
