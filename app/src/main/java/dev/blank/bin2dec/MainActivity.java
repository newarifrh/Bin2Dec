package dev.blank.bin2dec;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

import dev.blank.bin2dec.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.etBin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) return;

                if (!charSequence.toString().matches("\\b[01]+\\b")) {
                    int selectionStart = binding.etBin.getSelectionStart();
                    String result = charSequence.toString().replaceAll("[^0-1.]", "");
                    binding.etBin.setText(result);
                    binding.etBin.setSelection(selectionStart-1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etBin.getText() == null || binding.etBin.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "please enter a binary value", Toast.LENGTH_SHORT).show();
                } else {
                    if (binding.etBin.length() <= 16) {
                        if (binding.cardDec.getVisibility() != View.VISIBLE) {
                            binding.cardDec.setVisibility(View.VISIBLE);
                            SlideToAbove();
                        }
                        ConvertBin2Dec(binding.etBin.getText().toString());
                    } else {
                        Toast.makeText(MainActivity.this, "sorry, the maximum length of the binary value is 16", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void ConvertBin2Dec(String bin) {
        double total = 0;
        int idx = 0;
        for (int i = bin.length() - 1; i >= 0; i--) {
            total = total + (Double.parseDouble(String.valueOf(bin.charAt(i))) * (Math.pow(2, idx)));
            idx++;
        }
        DecimalFormat format = new DecimalFormat("0.#");
        binding.tvDec.setText(format.format(total));
    }

    public void SlideToAbove() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_layout_decimal);
        binding.cardDec.setAnimation(animation);
    }
}