package id.weplus.desclaimer;

import id.weplus.R;

public enum ModelObject {

    RED(R.string.telusuriweplus, R.layout.activity_splash),
    BLUE(R.string.telusuriweplus, R.layout.activity_beli_polis),
    GREEN(R.string.telusuriweplus, R.layout.activity_splash);

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
