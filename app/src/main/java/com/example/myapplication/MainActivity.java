package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.*;


public class MainActivity extends Activity {
	private EditText txtCmd;
	private Button btnCmd;
	private TextView output;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TextView t = new TextView(this);
		//t.setText(ping());
		//setContentView(t);
		setContentView(R.layout.activity_main);
		txtCmd = (EditText) findViewById(R.id.txtcmd);
		btnCmd = (Button) findViewById(R.id.btncmd);
		output = (TextView) findViewById(R.id.output);
		txtCmd.requestFocus();
		btnCmd.setEnabled(false);
		//txtCmd.setOnKeyListener(mKeyListener);
		txtCmd.addTextChangedListener(mTextWatcher);
		btnCmd.setOnClickListener(mClickListener);
	}
	
	private TextWatcher mTextWatcher = new TextWatcher(){
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count){}
	
		@Override
		public void afterTextChanged(Editable s){
			btnCmd.setEnabled(
				txtCmd
					.getText()
					.length() > 0);
		}
	};
	private OnClickListener mClickListener = new OnClickListener(){
		@Override
		public void onClick(View v){
			process();
		}
	};
	
	private String command(String cmd){
		String result = ((output.getText().length()>0)?"\n":"")+"Command: "+cmd+"\n";
		String command=cmd;
		try {
			Process p=Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader=new
			BufferedReader(new InputStreamReader(p.getInputStream()));
			String line=reader.readLine();
			while(line!=null) {
				result+=line+"\n";
				line=reader.readLine();
			}
		}
		catch(IOException e1) {
			result+="Command Error\n";
} 
		catch(InterruptedException e2) {}
		//System.out.println("Done");
		return result;
	}
	public void process(){
		String cmd = txtCmd.getText().toString();
		if (cmd.equals("clear"))
			output.setText("");
		else
			output.setText(output.getText().toString()
			+command(cmd));
		txtCmd.setText("");
		txtCmd.requestFocus();
	}
	
	public void checkInput(int keyCode){
		btnCmd.setEnabled((txtCmd.getText().length() > 0));
		if (keyCode == KeyEvent.KEYCODE_ENTER)
			process();
	}
}
