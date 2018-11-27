DiffRecycler
============

帮助开发者在使用RecyclerView的时候，减少代码量并提高开发效率。

- 默认支持DiffUtil，支持数据更新后刷新局部UI。

如何引用
----

```groovy
android {
    // 启用DataBinding
    dataBinding {
        enabled = true //should support dataBinding feature
    }
}
dependencies {
    // compile maven
    implementation 'com.neulion.android.diff-recycler:core:1.0.2-SNAPSHOT'
    implementation 'com.neulion.android.diff-recycler:processor-api:1.0.2-SNAPSHOT'
    annotationProcessor 'com.neulion.android.diff-recycler:processor:1.0.2-SNAPSHOT'
}
```

如何使用
----

- 在xml中申明DiffRecyclerView替代RecyclerView
- dividerColor、dividerSize 添加默认的分割线
- layoutType、layoutOrientation
  添加默认的layoutManager（LinearLayoutManager,GridLayoutManager,StaggeredGridLayoutManager）
- enableItemTouch 是否开启默认的长按拖拽默认，左右滑动删除默认
```xml
<com.neulion.android.diffrecycler.DiffRecyclerView
    android:id="@+id/recycler_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fadeScrollbars="false"
    android:scrollbarStyle="insideOverlay"
    android:scrollbars="vertical"
    app:dividerColor="@color/colorAccent"
    app:dividerSize="4dp"
    app:enableItemTouch="true"
    app:layoutOrientation="vertical"
    app:layoutType="linearLayout" />
```

如果只是一个简单的List页面，不需要自定义Adapter，使用DiffRecyclerSimpleAdapter
```java
class ExampleActivity extends Activity { 
    @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_example);
   
    //整个List只支持单一布局，R.layout.adapter_grid_item
    mListAdapter = new DiffRecyclerSimpleAdapter<>(getLayoutInflater(), R.layout.adapter_grid_item, mOnItemClickListener);
    mListAdapter.setData(DataProvider.getData());
    recyclerView.setAdapter(mListAdapter);
    }
    
    // List默认点击事件
    OnItemClickListener<T> mOnItemClickListener = new OnItemClickListener<T>(){
        @Override
        public void onItemClick(View view, T t){
            //do somethings
        }
    };
}
```

>   R.layout.adapter_grid_item
```xml
<layout
    xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <variable
            name="data"
            type="xxx" />
        <variable
            name="itemClickListener"
            type="com.neulion.android.diffrecycler.listener.OnItemClickListener" />
    </data>

    <RelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="96dp"
        android:foreground="?android:attr/selectableItemBackground"
        android:onClick="@{(view) -> itemClickListener.onItemClick(view , data)}">
        <!--...-->
    </RelativeLayout>
</layout>
```

**数据型T** 必须继承 **DataDiffCompare\<T>**
> UIData 一个栗子

```java
public class UIData implements DataDiffCompare<UIData>
{
    @DiffItem //用于DiffUtil比较是否是同一个对象
    String id;
    @DiffItem
    String name;
    @DiffContent//用于DiffUtil比较同一个对象的内容是否相同
    String description;
    @DiffContent
    int imageRes;

    public UIData(int id, String name, String description, @DrawableRes int imageRes)
    {
        this.id = String.valueOf(id);
        this.name = name;
        this.description = description;
        this.imageRes = imageRes;
    }

    @Override
    public boolean compareData(@NonNull UIData o)
    {
        // 判断是不是同一个对象，推荐返回主键的对比结果
        return getId().equals(o.getId()) && getName().equals(o.getName());
    }
}
```
