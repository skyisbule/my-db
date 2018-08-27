package com.github.skyisbule.db.index;

import com.github.skyisbule.db.page.Page;

public interface Index {

    public Page getPageByPK(int keyId);

}
