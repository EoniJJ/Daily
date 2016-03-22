package com.zzj.daily.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiaoJ on 2016/3/8.
 * 单条详细新闻实体类
 */
public class NewsEntity implements Serializable{

    private static final long serialVersionUID = -7316863953889550877L;
    /**
     * body :
     * image_source : Yestone.com 版权图片库
     * title : 深夜惊奇 · 不会撩妹
     * image : http://pic2.zhimg.com/fa50d1bf1dea2ec14e1acf2347e85f49.jpg
     * share_url : http://daily.zhihu.com/story/7975044
     * js : []
     * ga_prefix : 030822
     * section : {"thumbnail":"http://pic3.zhimg.com/520fd57c39a85848e82135468517096e.jpg","id":1,"name":"深夜惊奇"}
     * type : 0
     * id : 7975044
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=77778"]
     */

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private String ga_prefix;
    /**
     * thumbnail : http://pic3.zhimg.com/520fd57c39a85848e82135468517096e.jpg
     * id : 1
     * name : 深夜惊奇
     */

    private SectionEntity section;
    private int type;
    private int id;
    private List<?> js;
    private List<String> css;

    public void setBody(String body) {
        this.body = body;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public void setSection(SectionEntity section) {
        this.section = section;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJs(List<?> js) {
        this.js = js;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public String getBody() {
        return body;
    }

    public String getImage_source() {
        return image_source;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getShare_url() {
        return share_url;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public SectionEntity getSection() {
        return section;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public List<?> getJs() {
        return js;
    }

    public List<String> getCss() {
        return css;
    }

    public static class SectionEntity implements Serializable{
        private static final long serialVersionUID = -490671162324767183L;
        private String thumbnail;
        private int id;
        private String name;

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
