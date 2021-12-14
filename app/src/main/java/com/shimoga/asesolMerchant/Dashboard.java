package com.shimoga.asesolMerchant;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shimoga.asesolMerchant.Database.Database;
import com.shimoga.asesolMerchant.Model.CategoryOld;
import com.shimoga.asesolMerchant.Model.Order;
import com.shimoga.asesolMerchant.ViewHolder.CartAdapter;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;


public class Dashboard extends AppCompatActivity {
    private static final int CALL_PERMISSION_CODE = 100;

    Button btnLogout;
    FirebaseUser firebaseUser;
    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;
    RecyclerView recyclerView;

    int skinLess=0,cleaned=0;
    double totalAmt = 0;
    String nameOfTheFood="";
    String newNameOfTheFood="";
    int newPrice=0;
    String strNewPrice="";

    TextView food_name, food_price, food_description, food_kg, food_kg1,tvOutOfStock;
    ImageView food_image;
    EditText weightFood;
    TextView txtTotalPrice;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    Button gotoCart, btnWhatsapp, btnCall;
    ElegantNumberButton numberButton;
    CardView weigtdiv;
    Switch aSwitch1, aSwitch2, aSwitch3;
    String foodId = "";
    FirebaseDatabase database;
    DatabaseReference foods;

    CategoryOld currentFood;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        aSwitch1 = findViewById(R.id.switch1);
        aSwitch2 = findViewById(R.id.switch2);
        aSwitch3 = findViewById(R.id.switch3);

        //firebase init
        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Category");

        //init view
        numberButton = findViewById(R.id.number_button);
        btnCart = findViewById(R.id.btnCart);
        gotoCart = findViewById(R.id.btngotoCart);
        txtTotalPrice = findViewById(R.id.tvtotal);
        weigtdiv = findViewById(R.id.weightDiv);
        btnWhatsapp = findViewById(R.id.btnWhatsapp);
        btnCall = findViewById(R.id.btnCall);
        weightFood = findViewById(R.id.edtWeight);
        tvOutOfStock=findViewById(R.id.tvOutOfStock);

        loadListFood();

        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.shimoga.asesolMerchant.Dashboard.this, WhatsappActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = "+918553777130";
                String s = "tel:" + mobile;
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(s));
                if (ActivityCompat.checkSelfPermission(com.shimoga.asesolMerchant.Dashboard.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(com.shimoga.asesolMerchant.Dashboard.this, "You don't have permission call!", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(com.shimoga.asesolMerchant.Dashboard.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            CALL_PERMISSION_CODE);
                } else {
                    startActivity(intent);
                }
            }
        });

        gotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.shimoga.asesolMerchant.Dashboard.this, Cart.class);
                startActivity(intent);
                finish();
            }
        });

        food_description = findViewById(R.id.food_description);
        food_name = findViewById(R.id.food_name);
        food_price = findViewById(R.id.food_price);
        food_image = findViewById(R.id.img_food);
        food_kg = findViewById(R.id.food_kg);
        food_kg1 = findViewById(R.id.food_kg1);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);



        //get food id from intent
        if (getIntent() != null)
            foodId = getIntent().getStringExtra("FoodId");
        if (!foodId.isEmpty()) {
            getDetailFood(foodId);
        } else
            Toast.makeText(this, "sorry... error occurred", Toast.LENGTH_SHORT).show();

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentFood.getOutOfStock().equals("YES"))
                    Toast.makeText(com.shimoga.asesolMerchant.Dashboard.this, "This Product is not available !", Toast.LENGTH_SHORT).show();
                else
                    addFoodsToCart();
            }
        });

        aSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    aSwitch2.setVisibility(View.GONE);
                    aSwitch3.setVisibility(View.GONE);
                    weigtdiv.setVisibility(View.VISIBLE);

                    btnCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            addFoodsToCartbyHand();
                        }
                    });

                } else {
                    aSwitch2.setVisibility(View.VISIBLE);
                    aSwitch3.setVisibility(View.VISIBLE);
                    weigtdiv.setVisibility(View.GONE);

                    btnCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            addFoodsToCart();
                        }
                    });

                }
            }
        });

        aSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    //cleaned on
                    aSwitch1.setVisibility(View.GONE);
                    aSwitch3.setVisibility(View.GONE);
                    newNameOfTheFood=nameOfTheFood+" Cleaned";
                    newPrice=Integer.parseInt(currentFood.getPrice())+20;
                    food_name.setText(newNameOfTheFood);
                    food_price.setText(String.valueOf(newPrice));
                    currentFood.setPrice(String.valueOf(newPrice));
                    currentFood.setName(newNameOfTheFood);

                } else {
                    //cleaned off
                    aSwitch1.setVisibility(View.VISIBLE);
                    aSwitch3.setVisibility(View.VISIBLE);
                    newNameOfTheFood=nameOfTheFood;
                    newPrice=Integer.parseInt(currentFood.getPrice())-20;
                    food_name.setText(nameOfTheFood);
                    food_price.setText(String.valueOf(newPrice));
                    currentFood.setPrice(String.valueOf(newPrice));
                    currentFood.setName(nameOfTheFood);
                }
            }
        });

        aSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //skinless on
                    aSwitch1.setVisibility(View.GONE);
                    aSwitch2.setVisibility(View.GONE);
                    newNameOfTheFood=nameOfTheFood+" Skinless";
                    newPrice=Integer.parseInt(currentFood.getPrice())+30;
                    food_name.setText(newNameOfTheFood);
                    food_price.setText(String.valueOf(newPrice));
                    currentFood.setPrice(String.valueOf(newPrice));
                    currentFood.setName(newNameOfTheFood);

                } else {
                    //skinless off
                    aSwitch1.setVisibility(View.VISIBLE);
                    aSwitch2.setVisibility(View.VISIBLE);
                    newNameOfTheFood=nameOfTheFood;
                    newPrice=Integer.parseInt(currentFood.getPrice())-30;
                    food_name.setText(nameOfTheFood);
                    food_price.setText(String.valueOf(newPrice));
                    currentFood.setPrice(String.valueOf(newPrice));
                    currentFood.setName(nameOfTheFood);
                }
            }
        });

    }

    private void getDetailFood(final String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(CategoryOld.class);

                //set image
                Picasso.get().load(currentFood.getImage())
                        .into(food_image);

                if (currentFood.getName().equals("White Eggs") || currentFood.getName().equals("Brown Eggs")) {
                    aSwitch1.setVisibility(View.GONE);
                    aSwitch2.setVisibility(View.GONE);
                    aSwitch3.setVisibility(View.GONE);
                    //food_kg.setText("/Egg");
                    food_kg1.setText("Egg");
                    numberButton.setRange(5,60);
                    numberButton.setNumber("5");
                    numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                        @Override
                        public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                            if (newValue > oldValue) {
                                oldValue += 5;
                                numberButton.setNumber(String.valueOf(oldValue));
                            }
                            else {
                                oldValue -= 5;
                                if (oldValue<=5)
                                    numberButton.setNumber(String.valueOf(oldValue));
                                else
                                    numberButton.setNumber(String.valueOf(oldValue));
                            }
                        }
                    });
                }

                collapsingToolbarLayout.setTitle(currentFood.getName());
                food_price.setText(currentFood.getPrice());
                food_name.setText(currentFood.getName());
                food_description.setText(currentFood.getDescription());

                newPrice=Integer.parseInt(currentFood.getPrice());

                nameOfTheFood=currentFood.getName();

                if (currentFood.getOutOfStock().equals("YES"))
                {
                    tvOutOfStock.setText("Out of Stock");
                    tvOutOfStock.setTextColor(Color.RED);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadListFood() {
        cart = new Database(this).getCarts();

        //Price Calculation

        for (Order order : cart)
            totalAmt += (Double.parseDouble(order.getPrice())) * (Double.parseDouble(order.getQuantity()));
        Locale locale = new Locale("en", "IN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);


        txtTotalPrice.setText("Total: " + fmt.format(totalAmt));
    }

    private void addFoodsToCart() {
        new Database(getBaseContext()).addToCart(new
                Order(foodId,
                currentFood.getName(),
                numberButton.getNumber(),
                currentFood.getPrice(),
                currentFood.getDiscount()
        ));
        Toast.makeText(com.shimoga.asesolMerchant.Dashboard.this, "Added to Cart", Toast.LENGTH_SHORT).show();
        loadListFood();
    }

    private void addFoodsToCartbyHand() {
        double weightx=Double.parseDouble(weightFood.getText().toString().trim());
        weightx=round(weightx,2);
        weightx=weightx/1000;

        new Database(getBaseContext()).addToCart(new
                Order(foodId,
                currentFood.getName(),
                String.valueOf(weightx),
                currentFood.getPrice() ,
                currentFood.getDiscount()
        ));
        loadListFood();
    }

    public static double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
