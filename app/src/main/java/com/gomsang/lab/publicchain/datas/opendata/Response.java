package com.gomsang.lab.publicchain.datas.opendata;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by laino on 2018. 1. 15..
 */

@Root(name = "response")
public class Response {
    @Element(name = "header")
    private Header header;

    @Element(name = "body")
    private Body body;

    public void setHeader(Header header) {
        this.header = header;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public Body getBody() {
        return body;
    }

    public static Response failed() {
        return new Response();
    }
}

