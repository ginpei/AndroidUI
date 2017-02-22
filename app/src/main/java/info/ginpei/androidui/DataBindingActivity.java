package info.ginpei.androidui;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import info.ginpei.androidui.databinding.ActivityDataBindingBinding;

public class DataBindingActivity extends AppCompatActivity {

    public static final String TAG = "G#DataBindingActivity";
    public ViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vm = new ViewModel();

        ActivityDataBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        binding.setActivity(this);
        binding.setVm(vm);
    }

    public void updateButton_click(View view) {
        Log.d(TAG, "onClick() called");
        vm.setMessage("Hello Updated World!!");
    }

    public class ViewModel extends BaseObservable {

        public String message = "Hello!";

        @Bindable
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
            notifyPropertyChanged(BR.message);
        }

        public void message_textChanged(CharSequence charSequence, int i, int i1, int i2) {
            setMessage(charSequence.toString());
        }
    }
}
