package com.bjw.listview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();
/*    private String[] data = {"Apple", "Banana", "Orange",
            "Watermelon", "Pear", "Grape", "Pineapple",
            "Strawberry", "Cherry", "Mango"};*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFruits();//初始化水果数据
        FruitAdaper adapter = new FruitAdaper(
                MainActivity.this,R.layout.fruit_item, fruitList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                Fruit fruit = fruitList.get(i);
                Toast.makeText(MainActivity.this,fruit.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initFruits () {
        Fruit apple = new Fruit("Apple", R.drawable.apple_pic);
        fruitList.add(apple);
        Fruit orange = new Fruit("orange",R.drawable.orange_pic);
        fruitList.add(orange);
        Fruit apple1 = new Fruit("banana", R.drawable.banana_pic);
        fruitList.add(apple1);
        Fruit orange2 = new Fruit("orange",R.drawable.orange_pic);
        fruitList.add(orange2);
        Fruit apple2 = new Fruit("banana", R.drawable.banana_pic);
        fruitList.add(apple2);
        Fruit orange3 = new Fruit("orange",R.drawable.orange_pic);
        fruitList.add(orange3);
        Fruit apple4 = new Fruit("banana", R.drawable.banana_pic);
        fruitList.add(apple4);
    }
}
