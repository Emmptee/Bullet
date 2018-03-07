package com.souha.bullet.news.adapter.holder;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.just.library.AgentWeb;
import com.just.library.AgentWebSettings;
import com.just.library.WebDefaultSettingsManager;
import com.souha.bullet.R;
import com.souha.bullet.Utils.HtmlParser;
import com.souha.bullet.base.listener.OnItemClickListener;
import com.souha.bullet.data.NewEle;
import com.souha.bullet.databinding.ItemNewDetailVideoBinding;
import com.souha.bullet.widget.NoScrollWebView;


public class NewDetailVideoHolder extends RecyclerView.ViewHolder {

    private static final String IFRAME = "<iframe";
    private static final String IFRAME_AUTO = "<iframe style='max-width:100%;height:200;'";

    private ItemNewDetailVideoBinding binding;
    private OnItemClickListener<NewEle> listener;
    public AgentWeb mAgentWeb;
    public WebView webView;

    public NewDetailVideoHolder(ItemNewDetailVideoBinding binding,
                                OnItemClickListener<NewEle> listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
        initWebView();
    }

    public void bind(NewEle bean) {
        String htmlData = bean.html.replace(IFRAME, IFRAME_AUTO);
        String html = HtmlParser.loadDataWithVideo(htmlData);
        webView.loadData(html, "text/html; charset=UTF-8", null);

        binding.setNewEle(bean);
        binding.executePendingBindings();
        binding.contentFl.requestFocus();
    }

    private void initWebView() {
        FrameLayout.LayoutParams lp =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

        webView = new NoScrollWebView(itemView.getContext());

        mAgentWeb = AgentWeb.with((Fragment) listener)//
                .setAgentWebParent(binding.contentFl, lp)//
                .closeDefaultIndicator()//
                .setWebView(webView)
                .setAgentWebWebSettings(getSettings())//
                .setWebViewClient(new WebViewClient())
                .setWebChromeClient(new WebChromeClient())
                .setReceivedTitleCallback((view1, title) -> {

                })
                .setSecurityType(AgentWeb.SecurityType.strict)
                .createAgentWeb()//
                .ready()//
                .go(null);

        setDesktopMode(webView, false);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setKeepScreenOn(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.f5));
    }

    public AgentWebSettings getSettings() {
        return WebDefaultSettingsManager.getInstance();
    }

    public void setDesktopMode(WebView webView, final boolean enabled) {
        WebSettings webSettings = webView.getSettings();

        final String newUserAgent;
        if (enabled) {
            newUserAgent = webSettings.getUserAgentString().replace("Mobile", "eliboM").replace("Android", "diordnA");
        } else {
            newUserAgent = webSettings.getUserAgentString().replace("eliboM", "Mobile").replace("diordnA", "Android");
        }

        webSettings.setUserAgentString(newUserAgent);
        webSettings.setUseWideViewPort(enabled);
        webSettings.setLoadWithOverviewMode(enabled);
        webSettings.setSupportZoom(enabled);
        webSettings.setBuiltInZoomControls(enabled);
    }

}
