package com.bob.page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Bob Jiang on 2016/10/26.
 */
public class PageInfo<T> extends Page implements Serializable {

    private static final long serialVersionUID = -100140779657263868L;

    private List<T> result;

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
