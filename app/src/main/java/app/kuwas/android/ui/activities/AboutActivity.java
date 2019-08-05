/*
 * Copyright (C) 2019 Brook Mezgebu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.kuwas.android.ui.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.yenepaySDK.PaymentOrderManager;
import com.yenepaySDK.PaymentResponse;
import com.yenepaySDK.YenePayPaymentActivity;
import com.yenepaySDK.errors.InvalidPaymentException;
import com.yenepaySDK.model.OrderedItem;

import java.util.ArrayList;
import java.util.List;

import app.kuwas.android.R;
import app.kuwas.android.ui.adapters.AboutItemsRecyclerAdapter;
import app.kuwas.android.ui.adapters.MenuSheetAdapter;
import app.kuwas.android.ui.adapters.UsedLibrariesRecyclerAdapter;
import app.kuwas.android.utils.Utils;

import static app.kuwas.android.utils.Utils.bindCustomTabsService;
import static app.kuwas.android.utils.Utils.dpToPx;
import static app.kuwas.android.utils.Utils.openPlayStore;
import static app.kuwas.android.utils.Utils.openUrlInCustomTab;

public class AboutActivity extends YenePayPaymentActivity {

    private PaymentOrderManager paymentManager = new PaymentOrderManager("2251" , "5birr4kuwas");
    private RecyclerView aboutCardsRecyclerView, usedLibrariesRecyclerView;

    private void handleTopMarginOnFAB(AppCompatImageButton button) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // the sdk is greater than lollipop; the app is being drawn under the status bar
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) button.getLayoutParams();
            marginParams.setMargins( 0, dpToPx(this, 32), 0, 0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindCustomTabsService(this, new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
                client.warmup(0);
                client.newSession(null);
                Log.d("CHROME", name.flattenToShortString());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        });
    }

    @Nullable
    private View.OnClickListener clickToGo(String link) {
        return link != null && !link.isEmpty() ? v -> openUrlInCustomTab(this, link) : null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );

        setupPaymentProcess();
        handleTopMarginOnFAB(findViewById(R.id.back_button));
        findViewById(R.id.back_button).setOnClickListener(v -> onBackPressed());
        NestedScrollView mainScrollView = findViewById(R.id.container);

        //TODO: This about screen content should be fetched from remote config
        ArrayList<AboutItemsRecyclerAdapter.AboutItem> aboutItemArrayList = new ArrayList<>();
        aboutItemArrayList.add(new AboutItemsRecyclerAdapter.AboutItem("Kuwas" ,
                "Kuwas is an app designed to help people around " +
                        "the world get latest and accurate` information " +
                        "about the Ethiopian premier league. " , "", null));

        aboutItemArrayList.add(new AboutItemsRecyclerAdapter.AboutItem("Changelog",
                "Been working on kuwas for sometime now. " +
                        "First the library behind the app then the app itself. " +
                        "Click the button below to see the progress.", "ChangeLog", v -> {
            // open custom chrome tab to our change log
            openUrlInCustomTab(this, "https://github.com/brookmg/kuwas/releases");
        }));

        aboutItemArrayList.add(new AboutItemsRecyclerAdapter.AboutItem("Privacy Policy",
                "We do not take any personal information without" +
                        " your consent. You can read the full " +
                        "privacy policy by clicking the button below.", "Privacy Policy", v -> {
            // open custom chrome tab to our privacy log
            openUrlInCustomTab(this, "https://kuwas.flycricket.io/privacy.html");
        }));

        aboutItemArrayList.add(new AboutItemsRecyclerAdapter.AboutItem("Group",
                "Join our telegram group if you wish to know " +
                        "more about the upcoming updates and discuss " +
                        "about kuwas and what we should add or remove next.", "Telegram", v -> {
            // open telegram
            openUrlInCustomTab(this, "https://t.me/kuwasappgroup");
        }));

        aboutItemArrayList.add(new AboutItemsRecyclerAdapter.AboutItem("Support Development",
                "If you like the app and feel like Ethiopian " +
                        "football is not being appreciated much,  " +
                        "feel free to support the development of this app.", "Support", v -> {
            // show donate dialog
            showBottomSheetDialog();
        }, R.drawable.yenepay));

        aboutItemArrayList.add(new AboutItemsRecyclerAdapter.AboutItem("Rate & Review",
                "Rate this app on playstore and also give us " +
                        "your honest review to support this app and  " +
                        "inorder to figure out what's priority.", "PlayStore", v -> {
            openPlayStore(this);
        }, ContextCompat.getColor(this, R.color.green_0), ContextCompat.getColor(this, R.color.white_0)));

        ArrayList<UsedLibrariesRecyclerAdapter.LibItem> libItems = new ArrayList<>();

        libItems.add(new UsedLibrariesRecyclerAdapter.LibItem(
                "Soccer Ethiopia API" ,
                "BrookMG", clickToGo("https://github.com/brookmg/Soccer-Ethiopia-Api")));

        libItems.add(new UsedLibrariesRecyclerAdapter.LibItem(
                "YenePay" ,
                "YenePay", clickToGo("https://yenepay.com")));

        libItems.add(new UsedLibrariesRecyclerAdapter.LibItem(
                "Material Components" ,
                "Google", clickToGo("https://material.io")));

        libItems.add(new UsedLibrariesRecyclerAdapter.LibItem(
                "Lottie" ,
                "Airbnb", clickToGo("https://airbnb.io/lottie")));

        libItems.add(new UsedLibrariesRecyclerAdapter.LibItem(
                "Room" ,
                "Google", clickToGo("https://developers.android.com/jetpack/androix/releases/room")));

        libItems.add(new UsedLibrariesRecyclerAdapter.LibItem(
                "Firebase" ,
                "Google", clickToGo("https://firebase.com")));

        libItems.add(new UsedLibrariesRecyclerAdapter.LibItem(
                "Glide" ,
                "BumpTech", clickToGo("https://bumptech.github.io/glide")));

        aboutCardsRecyclerView = findViewById(R.id.main_content_recycler_view);
        aboutCardsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        aboutCardsRecyclerView.setAdapter(new AboutItemsRecyclerAdapter(aboutItemArrayList));
        aboutCardsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        usedLibrariesRecyclerView = findViewById(R.id.libraries_used_recycler_view);
        usedLibrariesRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        usedLibrariesRecyclerView.setAdapter(new UsedLibrariesRecyclerAdapter(libItems));
        usedLibrariesRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        try {
            if (getIntent().getBooleanExtra("goto_support" , false)) {
                mainScrollView.post(() -> mainScrollView.smoothScrollBy(0 , dpToPx(this, 500)));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void setupPaymentProcess() {
        paymentManager.setPaymentProcess(PaymentOrderManager.PROCESS_CART);
        paymentManager.setReturnUrl("app.kuwas.android:/dev_supported");

        paymentManager.setUseSandboxEnabled(false);  // TODO: set this to false when moving to production
        paymentManager.setShoppingCartMode(false);
    }

    private void startCheckOut(int price) throws InvalidPaymentException {

        paymentManager.addItem(new OrderedItem(
                price + "birr4kuwas",
                "Support Kuwas Development (" + price + "Br)",
                1,
                price
        ));
        paymentManager.startCheckout(this);

    }

    private void showBottomSheetDialog() {

        View bottomSheetContent = LayoutInflater.from(this).inflate(R.layout.support_dev_bottom_sheet_layout, null, false);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);

        RecyclerView recyclerView = bottomSheetContent.findViewById(R.id.menu_items_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        List<MenuSheetAdapter.MenuItem> items = new ArrayList<>();
        items.add(new MenuSheetAdapter.MenuItem(
                ContextCompat.getDrawable(this, R.drawable.ic_action_coffee) ,
                "5 BIRR" ,
                v -> {
                    try {
                        startCheckOut(5);
                    } catch (InvalidPaymentException e) {
                        e.printStackTrace();
                    }
                })
        );

        items.add(new MenuSheetAdapter.MenuItem(
                ContextCompat.getDrawable(this, R.drawable.ic_action_soda) ,
                "10 BIRR" ,
                v -> {
                    try {
                        startCheckOut(10);
                    } catch (InvalidPaymentException e) {
                        e.printStackTrace();
                    }
                })
        );

        items.add(new MenuSheetAdapter.MenuItem(
                ContextCompat.getDrawable(this, R.drawable.ic_action_cookie_with_fine_chips) ,
                "15 BIRR" ,
                v -> {
                    try {
                        startCheckOut(15);
                    } catch (InvalidPaymentException e) {
                        e.printStackTrace();
                    }
                })
        );

        items.add(new MenuSheetAdapter.MenuItem(
                ContextCompat.getDrawable(this, R.drawable.ic_action_hot_air_balloon) ,
                "25 BIRR" ,
                v -> {
                    try {
                        startCheckOut(25);
                    } catch (InvalidPaymentException e) {
                        e.printStackTrace();
                    }
                })
        );

        recyclerView.setAdapter(new MenuSheetAdapter(items));
        bottomSheetDialog.setContentView(bottomSheetContent);
        bottomSheetDialog.show();
    }


    @Override
    public void onPaymentResponseArrived(PaymentResponse response) {
        Bundle paymentInformation = new Bundle();
        paymentInformation.putString("User", response.getCustomerName() + " (" + response.getCustomerEmail() + ")");
        paymentInformation.putString(FirebaseAnalytics.Param.CURRENCY, "ETB");
        paymentInformation.putDouble(FirebaseAnalytics.Param.VALUE, response.getGrandTotal());
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.ECOMMERCE_PURCHASE, paymentInformation);
        Snackbar.make(usedLibrariesRecyclerView , "Thank you for your generosity. We will put your " + response.getGrandTotal() + " into good use." , 4_000).show();
    }

    @Override
    public void onPaymentResponseError(String error) {
        super.onPaymentResponseError(error);
        Snackbar.make(usedLibrariesRecyclerView , "Sorry. An error occured while processing your payment" , 4_000).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
