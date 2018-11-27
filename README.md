DiffRecycler
============

帮助开发者在使用RecyclerView的时候，减少代码量并提高开发效率。

- 支持DiffUtil，支持数据更新后刷新局部UI
- 支持左右滑动删除，长按拖拽
- 支持添加单击事件
- 支持添加头部，尾部（如果GridLayout布局，头部尾部自动占一行或一列）
- 支持在xml中添加LayoutManager（系统默认布局）
- 支持在xml中添加分割线（分割线可定义宽度和颜色）
- 支持更新数据（增加、删除、更新、移动）

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
    implementation 'com.neulion.android.diff-recycler:core:${version}'
    implementation 'com.neulion.android.diff-recycler:processor-api:${version}'
    annotationProcessor 'com.neulion.android.diff-recycler:processor:${version}'
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
    
    // List默认点击事件（可选）
    OnItemClickListener<T> mOnItemClickListener = new OnItemClickListener<T>(){
        @Override
        public void onItemClick(View view, T t){
            //do somethings
        }
    };
}
```

>R.layout.adapter_grid_item

variable **data** , **itemClickListener** 必须是这个名字！！！

variable **data** , **itemClickListener** 必须是这个名字！！！

variable **data** , **itemClickListener** 必须是这个名字！！！


```xml
<layout
    xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <variable
            name="data"
            type="UIData" />
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

**数据型T** 必须继承 **DataDiffCompare<T>**

**数据型T** 必须继承 **DataDiffCompare<T>**

**数据型T** 必须继承 **DataDiffCompare<T>**

>一个栗子

```java
public class UIData implements DataDiffCompare<UIData>
{
    @DiffItem //用于DiffUtil比较是否是同一个对象（可有多个,或者没有）
    String id;
    @DiffContent//用于DiffUtil比较同一个对象的内容是否相同（可有多个,或者没有）
    String description;

    public UIData(int id, String name, String description, @DrawableRes int imageRes)
    {
        this.id = String.valueOf(id);
        this.description = description;
    }

    @Override
    public boolean compareData(@NonNull UIData o)
    {
        // 判断是不是同一个对象，推荐返回主键的对比结果
        return getId().equals(o.getId()) && getName().equals(o.getName());
    }
}
```

打开日志
```
DiffRecyclerLogger.setDEBUG(boolean debug)
```

自定义Adapter
> 一个栗子
```java
public class CustomDiffRecyclerFragment extends BaseDiffRecyclerFragment
{
    // ...
    
    private class CustomRecyclerAdapter extends DiffRecyclerAdapter<UIData>
    {
        CustomRecyclerAdapter(LayoutInflater inflater)
        {
            super(inflater);
        }

        @Override
        public DiffViewHolder<UIData> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType)
        {
            return new CustomViewHolder(inflater.inflate(getViewHolderLayout(viewType), parent, false));
        }

        @Override
        public void onBindViewHolder(DiffViewHolder<UIData> holder, UIData data, int position)
        {
            ((ImageView) holder.findViewById(R.id.image)).setImageResource(data.getImageRes());

            ((TextView) holder.findViewById(R.id.index)).setText(String.valueOf(position));

            ((TextView) holder.findViewById(R.id.name)).setText(data.getName());

            ((TextView) holder.findViewById(R.id.description)).setText(data.getDescription());
        }

        @Override
        protected int getViewHolderLayout(int viewType)
        {
            return R.layout.adapter_list_common;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Holder
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private class CustomViewHolder extends DiffViewHolder<UIData> implements OnClickListener
    {
        CustomViewHolder(View itemView)
        {
            super(itemView);
        }

        @Override
        public void onClick(View v)
        {
            Toast.makeText(getActivity(), "单击事件:" + v.getTag(), Toast.LENGTH_SHORT).show();
        }
    }
}
```