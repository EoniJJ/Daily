package com.zzj.daily.conn;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/8.
 */
public class ZhiHuDailyApi {


    //http://news-at.zhihu.com/api/4/start-image/1080*1776 start-image后面为分辨率接受如下格式
    //       320*432
    //       480*728
    //       720*1184
    //       1080*1776
    public static String start_image = "http://news-at.zhihu.com/api/4/start-image/";

    //    URL 最后部分的数字代表所安装『知乎日报』的版本
    public static String version = "http://news-at.zhihu.com/api/4/version/android/";

    //    最新消息
    public static String latest_news = "http://news-at.zhihu.com/api/4/news/latest";

    //消息内容获取  后面拼接为消息的id
    public static String news = "http://news-at.zhihu.com/api/4/news/";

    //过往消息  若果需要查询 11 月 18 日的消息，before 后的数字应为 20131119
    public static String before_news = "http://news.at.zhihu.com/api/4/news/before/";

    //    新闻额外信息 后面拼接为消息的id
    public static String story_extra = "http://news-at.zhihu.com/api/4/story-extra/";
    //    新闻对应长评论查看 后面拼接为/id/long-comments
    public static String long_comments = "http://news-at.zhihu.com/api/4/story/";
    //    新闻对应短评论查看  后面拼接为/id/short-comments
    public static String short_comments = "http://news-at.zhihu.com/api/4/story/";
    //    主题日报列表查看
    public static String themes = "http://news-at.zhihu.com/api/4/themes";
    //    主题日报内容查看  后面拼接为主题日报的Id
    public static String theme = "http://news-at.zhihu.com/api/4/theme/";


}
