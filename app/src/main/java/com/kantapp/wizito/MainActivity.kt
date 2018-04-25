package com.kantapp.wizito

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kantapp.wizito.Model.ClientModel
import com.kantapp.wizito.Model.MobileCheck
import android.graphics.Bitmap
import android.view.LayoutInflater


class MainActivity : AppCompatActivity() {

    private var mobileTxt: EditText? = null
    private var nameTxt: EditText? = null
    private var emailTxt: EditText? = null
    private var companyTxt: EditText? = null
    private var clientSpinner: Spinner? = null
    private var checkMobileBtn: Button? = null
    private var appointBtn: Button? = null
    private var mobileExist = false
    private var clientid: String? = null
    private var imageCapture:ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mobileTxt = findViewById(R.id.mobileTxt)
        nameTxt = findViewById(R.id.nameTxt)
        emailTxt = findViewById(R.id.emailTxt)
        companyTxt = findViewById(R.id.companyTxt)
        checkMobileBtn = findViewById(R.id.checkMobileBtn)
        appointBtn = findViewById(R.id.appointBtn)

        imageCapture=findViewById<ImageView>(R.id.imageCapture)
        imageCapture!!.setOnClickListener {
            val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, 1888)
        }
        imageCapture!!.setImageResource(R.drawable.user)
        //Spinner Code Here
        clientSpinner = findViewById(R.id.clientSpinner)
        val clientsjson = "[\n" +
                "  {\n" +
                "    \"id\":\"1\",\n" +
                "    \"name\":\"Vinayak Kanchan\",\n" +
                "    \"mobile\":\"9769077986\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\":\"2\",\n" +
                "    \"name\":\"Sagar Palekar\",\n" +
                "    \"mobile\":\"0000000000\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\":\"3\",\n" +
                "    \"name\":\"Bipin\",\n" +
                "    \"mobile\":\"2222222222\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\":\"4\",\n" +
                "    \"name\":\"Nazim Patel\",\n" +
                "    \"mobile\":\"3333333333\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\":\"5\",\n" +
                "    \"name\":\"Rasika Mhtre\",\n" +
                "    \"mobile\":\"4444444444\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\":\"6\",\n" +
                "    \"name\":\"Ankur Palekar\",\n" +
                "    \"mobile\":\"5555555555\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\":\"7\",\n" +
                "    \"name\":\"Trishal\",\n" +
                "    \"mobile\":\"6666666666\"\n" +
                "  }\n" +
                "]"
        val clientModel = Gson().fromJson<List<ClientModel>>(clientsjson, object : TypeToken<List<ClientModel>>() {}.type)
        val clintListAdapter = ClientListAdapter(clientModel)
        clientSpinner!!.adapter = clintListAdapter

        clientSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                clientid = clientModel.get(position).id
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                clientid = ""
            }
        }

        checkMobileBtn!!.setOnClickListener {
            val mobileNumber = mobileTxt!!.text.toString()
            if (mobileNumber == "9769077985") {
                val data = "{\n" +
                        "  \"error\":false,\n" +
                        "  \"message\":\"Mobile Exist\",\n" +
                        "  \"visitor\":{\n" +
                        "    \"id\":1,\n" +
                        "    \"name\":\"Arvind Kant\",\n" +
                        "    \"mobile\":\"9769077985\",\n" +
                        "    \"email\":\"kanta1311@gmail.com\",\n" +
                        "    \"company\":\"Zeist Interactive LLP\"\n" +
                        "  }\n" +
                        "}"
                val visitor = Gson().fromJson(data, MobileCheck::class.java)
                nameTxt!!.setText(visitor.visitor.name)
                emailTxt!!.setText(visitor.visitor.email)
                companyTxt!!.setText(visitor.visitor.company)
                mobileExist=true
            } else {
                nameTxt!!.setText("")
                emailTxt!!.setText("")
                companyTxt!!.setText("")
                mobileExist=false
            }


        }

        appointBtn!!.setOnClickListener {
            val mobile = mobileTxt!!.text.toString()
            val email = emailTxt!!.text.toString()
            val fullname = nameTxt!!.text.toString()
            val company = companyTxt!!.text.toString()


            if(!isMobileValid(mobile) && mobile.length<10)
            {
                t("Please Enter Valid Mobile")
            }
            else if(fullname.length<6)
            {
                t("Please Enter Full Name")
            }
            else if(!isEmailValid(email))
            {
                t("Please Enter Valid Email ID")
            }
            else if(company.length<4)
            {
                t("Please Enter Comapany Name")
            }
            else if(clientid=="")
            {
                //t(clientid!!)
                t("Who You want to meet")
            }
            else
            {
//                Toast.makeText(this,"$mobile $email $fullname $company $clientid",Toast.LENGTH_LONG).show()
//                val intent=Intent(this,VerifyActivity::class.java);
//                intent.putExtra("mobile",mobile)
//                startActivity(intent)

                if(!mobileExist)
                {
                    val view=LayoutInflater.from(this).inflate(R.layout.otp_layout,null)
                    val alertBuilder=AlertDialog.Builder(this)
                    alertBuilder.setView(view)
                    alertBuilder.setNegativeButton("Close", DialogInterface.OnClickListener {
                        dialog, which ->
                        dialog.dismiss()
                    })
                    alertBuilder.setCancelable(false)
                    val alert=alertBuilder.create()
                    alert.show()

                    val otptxt=view.findViewById<EditText>(R.id.OTPtxt)
                    val optbtn=view.findViewById<Button>(R.id.OTPbtn)
                    optbtn.setOnClickListener {
                        val otp=otptxt.text.trim().toString()
                        if(otp=="1234")
                        {
                            Toast.makeText(this,"Your appointment is done Please wait...",Toast.LENGTH_LONG).show()
                            alert.dismiss()
                        }
                        else
                        {
//                            Toast.makeText(this,"Wrong otp",Toast.LENGTH_LONG).show()
                            otptxt.setError("Wrong OTP")
                        }
                    }
                }
                else
                {
                    Toast.makeText(this,"Your appointment is done Please wait...",Toast.LENGTH_LONG).show()
                }
            }

        }

    }

    fun t(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        Log.d("Arvind", message)
    }

    fun isMobileValid(mobile: String): Boolean {
        return android.util.Patterns.PHONE.matcher(mobile).matches();
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == 1888 && resultCode == Activity.RESULT_OK) {
            val extras = data.extras
            val imageBitmap = extras!!.get("data") as Bitmap
            imageCapture!!.setImageBitmap(imageBitmap)
        }

    }
}
