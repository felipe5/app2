package conceptosejemplo.com.conceptosejemplo.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import org.json.JSONException;
import java.util.ArrayList;
import conceptosejemplo.com.conceptosejemplo.dao.AssetDatabaseOpenHelper;
import conceptosejemplo.com.conceptosejemplo.dao.DataAccessObject;
import conceptosejemplo.com.conceptosejemplo.R;
import conceptosejemplo.com.conceptosejemplo.adapters.EmployeesListAdapter;
import conceptosejemplo.com.conceptosejemplo.model.Employee;

public class EmployeesPayroll extends AppCompatActivity {

    private ListView employeesList;
    private ArrayList<Employee> employeesArrayList = new ArrayList<>();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employees_payroll);

        employeesList = (ListView)findViewById(R.id.employees_list);

        try {
            DataAccessObject.getDao(getApplicationContext());
            AssetDatabaseOpenHelper databaseAdmin = new AssetDatabaseOpenHelper(getApplicationContext());
            SQLiteDatabase database = databaseAdmin.openDatabase();

            Cursor cursor = DataAccessObject.getDao(getApplicationContext()).getAllRows();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Employee employee = new Employee(
                                            cursor.getInt(cursor.getColumnIndex("id")),
                                            cursor.getString(cursor.getColumnIndex("name")),
                                            cursor.getString(cursor.getColumnIndex("function"))
                                        );
                    employeesArrayList.add(employee);
                    cursor.moveToNext();
                }
            }
            database.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        employeesList.setAdapter(new EmployeesListAdapter(getBaseContext(),employeesArrayList));

    }
}
