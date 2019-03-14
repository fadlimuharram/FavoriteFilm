package com.example.favoritefilm;

import android.content.Intent;
import android.database.Cursor;

public interface NowPlayingCallback {
    void showLoading();
    void hideLoading();
    void preExecuteFavorites();
    void postExecuteFavorites(Cursor result);
}
