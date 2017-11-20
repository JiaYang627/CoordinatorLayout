# CoordinatorLayout

> 此Demo为上一个Demo JYTabLayout的延伸。主要实现功能为ToolBar的隐藏和折叠。[JYTabLayout](https://github.com/JiaYang627/JYTabLayout)

先上UI图(无图说个XX啊)

## AndroidUI

**HiddenToolBar**

![HiddenToolBar](http://a2.qpic.cn/psb?/V14YlNrL2eQEkW/EXQtE5cEMkkt2OyZ.DHXIojhgIvnF8ygOohlMW1GDIA!/b/dA8BAAAAAAAA&bo=mgGOApoBjgICPRk!&rf=viewer_4)

***

**FoldingToolBar**

录屏的时候录屏软件渲染有问题，凑合着看吧。

![FoldingToolBar](http://a1.qpic.cn/psb?/V14YlNrL2eQEkW/mm9tMqgMxii96.wvM4bs.S9EQ8uLYvHXuPvlfnUCXQU!/b/dA4BAAAAAAAA&bo=aAEvAmgBLwICOh4!&rf=viewer_4)

***

* 此Demo是JYTabLayout的延伸，列表的展示等代码就不在细说。

配置build.gradle:

```
dependencies {
    ...
    compile 'com.android.support:appcompat-v7:25.3.1'
    compile 'com.android.support:design:25.3.1'
    compile 'com.android.support:recyclerview-v7:25.3.1'
    compile 'com.android.support:cardview-v7:25.3.1'
}
```

## HiddenToolBar

实现ToolBar的隐藏效果，使用系统的控件：CoordinatorLayout。CoordinatorLayout是Android Design Support Library 中比较难的控件。其为用来组织其子View之间协作的一个父View。ConrdinatorLayout默认情况下可被理解为一个FragLayout。

* 布局文件 我们将根布局LinearLayout更换为CoordinatorLayout。代码如下:


```
<?xml version="1.0" encoding="utf-8"?>
<android.support.design.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    >


    <android.support.design.widget.AppBarLayout
        android:id="@+id/appBarLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar">


        <android.support.v7.widget.Toolbar
            android:id="@+id/toolBar"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            app:layout_scrollFlags="scroll|enterAlways"
            app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>


        <android.support.design.widget.TabLayout
            android:id="@+id/tabLayout"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:tabIndicatorColor="#ADBE107E"
            app:tabMode="scrollable">

        </android.support.design.widget.TabLayout>

    </android.support.design.widget.AppBarLayout>

    <android.support.v4.view.ViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior">

    </android.support.v4.view.ViewPager>

</android.support.design.widget.CoordinatorLayout>

```

此布局中能实现隐藏ToolBar的关键是 ToolBar中 app:layout_scrollFlags="scroll|enterAlways" 标签，必须至少使用scroll这个Flag，这样ToolBar这个View才会滚出屏幕。

还有一点：需要将下面滑动的RecyclerView或者其他任意支持嵌套滚动的View添加 app:layout_behavior="@string/appbar_scrolling_view_behavior" 这个属性。这样才能在下面View发生滑动事件的时候，AppBarLayout接收到View的滑动/滚动事件。

例如：上面中如果ViewPager不添加app:layout_behavior="@string/appbar_scrolling_view_behavior"这个属性的话，运行代码后滑动效果是 先将ToolBar向上滑动至隐藏效果，然后下面的View才会向上滑动，无法做到下面的View和上面的ToolBar同时滑动。

Java中什么都不做什么改变，依旧是为ViewPager设置数据，将TabLayout与ViewPager关联起来，给TabLayout设置适配器。部分代码如下(具体可看JYTabLayout)：

```
 private void initViewPager() {
        List<String> titles = new ArrayList<>();

        for (int i = 0 ; i < mStrings.length ; i ++) {
            titles.add(mStrings[i]);
        }


        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0 ; i < mStrings.length ; i ++) {
            fragments.add(new ListFragment());
        }


        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), titles, fragments);

        //给ViewPager设置适配器
        mViewPager.setAdapter(fragmentAdapter);
        //将TabLayout和ViewPager关联起来。
        mTabLayout.setupWithViewPager(mViewPager);
        //给TabLayout设置适配器
        mTabLayout.setTabsFromPagerAdapter(fragmentAdapter);
    }
```

***

## FoldingToolBar

要实现ToolBar折叠效果，需要引入新的布局 CollapsingToolbarLayout，其作用是提供了一个可以折叠的ToolBar。CollapsingToolbarLayout继承自FrameLayout。

先上布局文件代码：


```
<?xml version="1.0" encoding="utf-8"?>
<android.support.design.widget.CoordinatorLayout

    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    >


    <android.support.design.widget.AppBarLayout
        android:id="@+id/appbar"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:fitsSystemWindows="true"
        android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar">

        <android.support.design.widget.CollapsingToolbarLayout
            android:id="@+id/collToolBarLayout"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:fitsSystemWindows="true"
            app:contentScrim="?attr/colorPrimary"
            app:expandedTitleMarginEnd="64dp"
            app:expandedTitleMarginStart="48dp"
            app:layout_scrollFlags="scroll|exitUntilCollapsed">

            <ImageView
                android:id="@+id/backdrop"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:fitsSystemWindows="true"
                android:scaleType="centerCrop"
                android:src="@drawable/mao"/>

            <android.support.v7.widget.Toolbar
                android:id="@+id/toolBar"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                app:layout_collapseMode="pin"
                app:popupTheme="@style/ThemeOverlay.AppCompat.Light">

            </android.support.v7.widget.Toolbar>

        </android.support.design.widget.CollapsingToolbarLayout>
    </android.support.design.widget.AppBarLayout>

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scrollbars="none"
        app:layout_behavior="@string/appbar_scrolling_view_behavior">

    </android.support.v7.widget.RecyclerView>

</android.support.design.widget.CoordinatorLayout>
```

其中需要注意的是 CollapsingToolbarLayout 的 app:layout_scrollFlags 属性，是控制包含在 CollapsingToolbarLayout 中控件在响应 下部View layout_behavior滑动事件 时做出响应的 scrollFlags滚动事件。

CollapsingToolbarLayout的几个关键属性简单说一下：

* app:contentScrim=""  用来设置CollapsingToolbarLayout收缩后到顶部的颜色。
* app:expandedTitleGravity="left|bottom"  表示将此CollaspingToolbarLayout完全展开后，title所处的位置，默认值为："left|bottom"。
* app:collapsedTitleGravity="left" 表示当头部的衬图ImageView 消失后，此title将回归到Toolbar的位置，默认值为left。
* app:layout_scrollFlags="" 此属性用来设置滚动事件，属性里必须至少有scroll这个Flag，这样此View才能滚出屏幕。此处我们使用的是 scroll|exitUntilCollapsed ，这样就能实现折叠效果。如果向实现隐藏效果，可以设置 scroll|enterAlways 。


ImageView控件中：
* layout_collapseMode 有两个值，pin 和 parallax。设置为pin的模式时，当CollapsingToolbarLayout完全收缩后，Toolbar还可以保留在屏幕上。当设置为parallax 模式时，在内容滚动时，CollapsingToolbarLayout中的View（比如ImageView)也可以同时滚动，实现视差滚动效果，通常和layout_collapseParallaxMultiplier(设置视差因子)搭配使用。
* layout_collapseParallaxMultiplier(视差因子) - 设置视差滚动因子，值为：0~1。

Toolbar控件中：
* layout_collapseMode 我们设置为 pin 模式。


Java代码中：

```
 @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbarfolding);

        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collToolBarLayout);
        collapsingToolbarLayout.setTitle("哆啦A梦");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new RecyclerViewAdapter(this));

        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.GREEN);//设置收缩后Toolbar上字体的颜色
    }
```

* 注意：使用CollapsingToolbarLayout时必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上不会显示。如：collapsingToolbarLayout.setTitle("哆啦A梦");

* 设置Title文字渐变颜色(系统会自动帮我们过渡)：

```
collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
collapsingToolbarLayout.setCollapsedTitleTextColor(Color.GREEN);//设置收缩后Toolbar上字体的颜色
```
