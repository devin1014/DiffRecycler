DiffRecycler
============

![Logo](xxx)

Help reduce your code when using RecyclerView

- adapter support DiffUtil feature



Download
--------
```groovy
android {
    defaultConfig {
        //...
        javaCompileOptions {
            annotationProcessorOptions {
                includeCompileClasspath true //find annotation processor in jar file,if you compile local file
            }
        }
    }
    dataBinding {
        enabled = true //should support dataBinding feature
    }
}
dependencies {
    // compile maven
    implementation 'com.neulion.android.diff-recycler:core:1.0.2-SNAPSHOT'
    implementation 'com.neulion.android.diff-recycler:processor-api:1.0.2-SNAPSHOT'
    annotationProcessor 'com.neulion.android.diff-recycler:processor:1.0.2-SNAPSHOT'
    
    // compile local file 
    //implementation(name: 'DiffRecycler', ext: 'aar')
    //implementation files('libs/DiffRecyclerProcessor_api.jar')
    //annotationProcessor files('libs/DiffRecyclerProcessor.jar')
}
```

How to use
----------
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

```java
class ExampleActivity extends Activity { 
    @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_example);
   
    //find DiffRecyclerView
    mRecyclerView = findViewById(R.id.recycler_view);
    //simple list adapter with only one view type
    mListAdapter = new DiffRecyclerSimpleAdapter<>(getLayoutInflater(), R.layout.adapter_grid_item, mOnItemClickListener);
    mListAdapter.setData(DataProvider.getData());
    recyclerView.setAdapter(mListAdapter);
    }
    
    OnItemClickListener<T> mOnItemClickListener = new OnItemClickListener<T>(){
        @Override
        public void onItemClick(View view, T t){
            //do somethings
        }
    };
}
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

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
        android:gravity="center_vertical"
        android:onClick="@{(view) -> itemClickListener.onItemClick(view , data)}"
        android:orientation="vertical"
        tools:background="#55888888">
        
        <!--...-->
        
    </RelativeLayout>

</layout>
```
