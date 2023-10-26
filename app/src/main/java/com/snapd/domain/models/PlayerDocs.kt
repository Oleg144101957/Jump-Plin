package com.snapd.domain.models

import android.net.Uri
import android.webkit.ValueCallback

interface PlayerDocs {
    fun pickPhotos(pickedPhotos : ValueCallback<Array<Uri>>?)

}