package com.example.c0777180_w2020_mad3125_fp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.c0777180_w2020_mad3125_fp.Adapters.BillListAdapter;
import com.example.c0777180_w2020_mad3125_fp.Models.Bill;
import com.example.c0777180_w2020_mad3125_fp.Models.Customer;
import com.example.c0777180_w2020_mad3125_fp.R;
import com.example.c0777180_w2020_mad3125_fp.Util.DataFormatting;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ShowBillDetailsActivity extends AppCompatActivity {


    @InjectView(R.id.customerID)
    TextView customerID;
    @InjectView(R.id.customerFullName)
    TextView customerFullName;
    @InjectView(R.id.customerEmail)
    TextView customerEmail;
    @InjectView(R.id.customerGender)
    TextView customerGender;
    @InjectView(R.id.customerDateOfBirth)
    TextView customerDateOfBirth;
    @InjectView(R.id.customerTotalBill)
    TextView customerTotalBill;
    AlertDialog.Builder builder;

    @InjectView(R.id.rvBillList)
    RecyclerView rvBillList;
    private BillListAdapter billListAdapter;
    private ArrayList<Bill> billArrayList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent in = new Intent(ShowBillDetailsActivity.this, AddNewBillActivity.class);
                Customer customer = getCustomer();
                in.putExtra("CurrentCustomer", customer);
                startActivityForResult(in, 1);
                return true;
            case R.id.item2:

                builder.setMessage("Are you sure you want to End Session?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent mIntent = new Intent(ShowBillDetailsActivity.this,LoginActivity.class);
                                startActivity(mIntent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("LOGOUT");
                alert.show();
                alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                alert.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bill_details);
        ButterKnife.inject(this);
        rvBillList.setHasFixedSize(true);
        builder = new AlertDialog.Builder(this);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Customer customer = getCustomer();


        billArrayList = populateBills();
        customerID.setText(customer.getCustomerID());
        customerFullName.setText(customer.getFirstName() + " " + customer.getLastName());
        customerDateOfBirth.setText(customer.getDateOfBirth());
        customerGender.setText(customer.getGender());
        customerEmail.setText(customer.getEmailID());
        customerTotalBill.setText(DataFormatting.stringToDouble(customer.calculateBill()));

        billListAdapter = new BillListAdapter(billArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvBillList.setLayoutManager(layoutManager);
        rvBillList.setAdapter(billListAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Customer customer = getCustomer();
        customerTotalBill.setText(DataFormatting.stringToDouble(customer.calculateBill()));
        billListAdapter.notifyDataSetChanged();
    }

    public Customer getCustomer() {
        Intent intent = getIntent();
        Customer customer = (Customer) intent.getParcelableExtra("CUSTOMERINFO");
        return customer;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK){
                Customer customer = data.getParcelableExtra("CUSTOMERINFO");
                billArrayList.clear();
                billArrayList.addAll(customer.getCustomerBills());
                billListAdapter.notifyDataSetChanged();
            }
        }
    }

    public ArrayList<Bill> populateBills() {
        billArrayList = new ArrayList<>();
        billArrayList = getCustomer().getCustomerBills();
        return billArrayList;
    }
}
