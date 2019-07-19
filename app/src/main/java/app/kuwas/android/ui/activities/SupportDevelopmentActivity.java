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

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.yenepaySDK.PaymentOrderManager;
import com.yenepaySDK.PaymentResponse;
import com.yenepaySDK.YenePayPaymentActivity;
import com.yenepaySDK.errors.InvalidPaymentException;
import com.yenepaySDK.model.OrderedItem;

import app.kuwas.android.R;

import static app.kuwas.android.utils.Utils.dpToPx;

public class SupportDevelopmentActivity extends YenePayPaymentActivity {

    private PaymentOrderManager paymentManager = new PaymentOrderManager("2251" , "5birr4kuwas");

    private void handleTopPaddingOnAppBarLayout(AppBarLayout appBarLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // the sdk is greater than lollipop; the app is being drawn under the status bar
            appBarLayout.setPadding(0, dpToPx(this, 24), 0, 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_development);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );

        setupPaymentProcess();
        findViewById(R.id.donate_5).setOnClickListener(v -> {
            try {
                startCheckOut(5);
            } catch (InvalidPaymentException e) {
                e.printStackTrace();
                Log.e("YenePayImpl" , e.toString());
            }
        });

        findViewById(R.id.donate_10).setOnClickListener(v -> {
            try {
                startCheckOut(10);
            } catch (InvalidPaymentException e) {
                e.printStackTrace();
                Log.e("YenePayImpl" , e.toString());
            }
        });

        findViewById(R.id.donate_15).setOnClickListener(v -> {
            try {
                startCheckOut(15);
            } catch (InvalidPaymentException e) {
                e.printStackTrace();
                Log.e("YenePayImpl" , e.toString());
            }
        });
    }

    private void setupPaymentProcess() {
        paymentManager.setPaymentProcess(PaymentOrderManager.PROCESS_CART);
        paymentManager.setReturnUrl("app.kuwas.android:/dev_supported");

        paymentManager.setUseSandboxEnabled(true);  // TODO: set this to false when moving to production
        paymentManager.setShoppingCartMode(false);
    }

    private void startCheckOut(int price) throws InvalidPaymentException {
        switch (price) {
            case 5: {
                paymentManager.addItem(new OrderedItem("5birr4kuwas", "Support Kuwas Development (5Br)", 1, 5.0));
                paymentManager.startCheckout(this);
                break;
            }

            case 10: {
                paymentManager.addItem(new OrderedItem("10birr4kuwas", "Support Kuwas Development (10Br)", 1, 10.0));
                paymentManager.startCheckout(this);
                break;
            }

            case 15: {
                paymentManager.addItem(new OrderedItem("15birr4kuwas", "Support Kuwas Development (15Br)", 1, 15.0));
                paymentManager.startCheckout(this);
                break;
            }

            default: {
                // we only have 5 birr ticket for now
                paymentManager.addItem(new OrderedItem("5birr4kuwas", "Support Kuwas Development (5Br)", 1, 5.0));
                paymentManager.startCheckout(this);
            }
        }
    }

    @Override
    public void onPaymentResponseArrived(PaymentResponse response) {
        super.onPaymentResponseArrived(response);
        Bundle paymentInformation = new Bundle();
        paymentInformation.putString("User", response.getCustomerName() + " (" + response.getCustomerEmail() + ")");
        paymentInformation.putString(FirebaseAnalytics.Param.CURRENCY, "ETB");
        paymentInformation.putDouble(FirebaseAnalytics.Param.VALUE, response.getGrandTotal());
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.ECOMMERCE_PURCHASE, paymentInformation);
    }

    @Override
    public void onPaymentResponseError(String error) {
        super.onPaymentResponseError(error);
        // Log this error
        Log.e("YenePayImpl" , error);
    }
}
