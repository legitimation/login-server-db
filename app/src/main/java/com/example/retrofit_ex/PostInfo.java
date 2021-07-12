package com.example.retrofit_ex;

public class PostInfo {
    private int __v;
    private String name, title, content, _id;
    public PostInfo(){}
    public PostInfo(String name, String title, String content){
        this.name=name;
        this.title=title;
        this.content=content;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int get__v() {
        return __v;
    }

    public String get_id() {
        return _id;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

}
