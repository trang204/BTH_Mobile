package com.example.tabselector2;

public class Item {
    private String maSo;
    private String tieuDe; // Rất quan trọng: Phải có trường này
    private int like; // 0 = unlike (trái tim rỗng), 1 = like (trái tim đỏ)

    public Item(String maSo, String tieuDe, int like) {
        this.maSo = maSo;
        this.tieuDe = tieuDe;
        this.like = like;
    }

    public String getMaSo() {
        return maSo;
    }

    public void setMaSo(String maSo) {
        this.maSo = maSo;
    }

    public String getTieuDe() { // Rất quan trọng: Phải có getter cho tiêu đề
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}