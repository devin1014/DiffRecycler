package com.neulion.android.diffrecycler;

import android.view.View;

interface HeaderFooterAdapter
{
    int addHeader(View header);

    int addHeader(View header, int index);

    int removeHeader(View header);

    View removeHeader(int index);

    void removeAllHeader();

    boolean isHeader(int position);

    boolean hasHeaders();

    int getHeaderCount();

    // ----------------------------------------------------------------------------------------------------------------------------------
    // - Footer
    // ----------------------------------------------------------------------------------------------------------------------------------
    int addFooter(View footer);

    int addFooter(View footer, int index);

    int removeFooter(View footer);

    View removeFooter(int index);

    void removeAllFooter();

    boolean isFooter(int position);

    boolean hasFooters();

    int getFooterCount();
}
