package com.younchen.younsampleproject.material.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/23.
 */

public class Contact implements Serializable{

    public String name;
    public String headImageUrl;
    public String message;

    public int id;
    public int status;
    public int presence;
    public int photoId;
    public String lookUpKey;
    public int stared;
}
