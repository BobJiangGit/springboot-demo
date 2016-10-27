package com.bob.page;

import com.bob.constants.PageConstant;

import java.io.Serializable;

/**
 * Created by Bob Jiang on 2016/10/12.
 */
public class Page implements Serializable {

    private static final long serialVersionUID = -390034001595630136L;

    private int pageNum;    //当前页
    private int pageSize;   //每页显示行数
    private int totalPage;  //总页数
    private int totalRow;   //总行数

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum > 0 ? pageNum : 1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize > 0 ? pageSize : PageConstant.PAGE_SIZE;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }
}
