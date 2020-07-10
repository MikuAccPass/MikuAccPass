package com.example.mikuaccpass;

import android.app.assist.AssistStructure;
import android.content.SharedPreferences;
import android.os.CancellationSignal;
import android.service.autofill.AutofillService;
import android.service.autofill.Dataset;
import android.service.autofill.FillCallback;
import android.service.autofill.FillContext;
import android.service.autofill.FillRequest;
import android.service.autofill.FillResponse;
import android.service.autofill.SaveCallback;
import android.service.autofill.SaveRequest;
import android.view.autofill.AutofillValue;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MikuAutofillService extends AutofillService {
    private List<AssistStructure.ViewNode> fields = new ArrayList<>();
    private List<Acount> accountList = new ArrayList<>();
    private SharedPreferences preferences;

    @Override
    public void onFillRequest(@NonNull FillRequest fillRequest, @NonNull CancellationSignal cancellationSignal, @NonNull FillCallback fillCallback) {
        preferences = getSharedPreferences("setting", MODE_PRIVATE);
        accountList.clear();
        fields.clear();

        if (preferences.getBoolean("autofill_enable", false)) {
            getAccounts();
            if(accountList.size() == 0)
                return;

            List<FillContext> context = fillRequest.getFillContexts();
            AssistStructure structure = context.get(context.size() - 1).getStructure();
            traverseStructure(structure);
            if(fields.size() == 0)
                return;

            FillResponse.Builder responseBuilder = new FillResponse.Builder();
            for (int i = 0; i < fields.size(); i++) {
                for (int j = 0; j < accountList.size(); j++) {
                    RemoteViews usernamePresentation = new RemoteViews(getPackageName(), android.R.layout.simple_list_item_1);
                    usernamePresentation.setTextViewText(android.R.id.text1, accountList.get(j).getStation());

                    Dataset loginDataSet = new Dataset.Builder()
                            .setValue(fields.get(i).getAutofillId(), AutofillValue.forText(accountList.get(j).getPassword()), usernamePresentation)
                            .build();

                    responseBuilder.addDataset(loginDataSet);
                }
            }

            fillCallback.onSuccess(responseBuilder.build());
        }
    }

    @Override
    public void onSaveRequest(@NonNull SaveRequest saveRequest, @NonNull SaveCallback saveCallback) {

    }

    public void traverseStructure(AssistStructure structure) {
        int nodes = structure.getWindowNodeCount();

        for (int i = 0; i < nodes; i++) {
            AssistStructure.WindowNode windowNode = structure.getWindowNodeAt(i);
            AssistStructure.ViewNode viewNode = windowNode.getRootViewNode();
            traverseNode(viewNode);
        }
    }

    public void traverseNode(AssistStructure.ViewNode viewNode) {
        if (viewNode.getInputType() == 0x00000081
                || viewNode.getInputType() == 0x00000091
                || viewNode.getInputType() == 0x000000e1
                || viewNode.getInputType() == 0x00000012)
            fields.add(viewNode);

        for (int i = 0; i < viewNode.getChildCount(); i++) {
            AssistStructure.ViewNode childNode = viewNode.getChildAt(i);
            traverseNode(childNode);
        }
    }

    private void getAccounts() {
        accountList.clear();
        preferences = getSharedPreferences("share", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int n = preferences.getInt("number", 0);
        editor.putInt("number", n);
        for (int i = 1; i <= n; i++) {
            String appname = i + "";
            appname = preferences.getString(appname, "");//获取appname为命名的相关sharepreference文件夹名字
            SharedPreferences pref = getSharedPreferences(appname, MODE_PRIVATE);
            if(!(pref.getString("origin_appname","").equals(""))){
            String appkey = pref.getString("appkey", "");
            InfoStorage infostorage = null;
            String[] content = infostorage.readInfo(this, appname, appkey);
            Acount x = new Acount(appname, content[0], content[1], R.drawable.ic_launcher_background);
            accountList.add(x);}
        }
        editor.apply();
    }
}
