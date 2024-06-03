package com.example.trashure.ui.banksampah

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trashure.R
import com.example.trashure.databinding.FragmentBankSampahBinding

class BankSampahFragment : Fragment() {
    private var _binding: FragmentBankSampahBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: BankSampahAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBankSampahBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val valuePlace = listOf(
            BankSampahDataTempat(
                "RPM Waste4Change Bekasi",
                "Rumah Pemulihan Material (RPM) Komplek Kantor Waste4Change Vida Bumipala, Jl. Alun Alun Utara, RT.003/RW.005, Padurenan, Kec. Mustika Jaya, Kota Bekasi, Jawa Barat 17156"
            ),
            BankSampahDataTempat(
                "Kita Olah Indonesia",
                "Jl. Koperpu XX, RT.002/RW.006, Sumur Batu, Kec. Bantar Gebang, Kota Bks, Jawa Barat 17154 (Bangunan Pagar Biru)"
            ),
            BankSampahDataTempat(
                "Bank Sampah Bina Warga Mandiri",
                "Jl. Bekasi Timur Raya KM 23 No.13, RT.18/RW.4, Cakung Bar., Kec. Cakung, Kota Jakarta Timur, Daerah Khusus Ibukota Jakarta 13910"
            ),
            BankSampahDataTempat(
                "Cendana Peduli - Cakung",
                "Komplek RGTC RT.004/RW.10, Blok Cendana Kios Lt. Dasar No. 14 Tipar Cakung, Cakung Barat, Kota Jakarta Timur, Daerah Khusus Ibukota Jakarta 13910"
            ),
            BankSampahDataTempat(
                "Bank Sampah Nanas",
                "Jl. Nanas X No.21, Gunung Putri, Blok B 19 No 19 Rt 07 Rw 12 Desa Karangan Kec. Gunung Putri, Kabupaten Bogor, Jawa Barat 16961"
            ),
            BankSampahDataTempat(
                "Bank Sampah Induk Rumah Harum",
                "Jl. Merdeka No.1, RT.05/RW.01, Abadijaya, Kec. Sukmajaya, Kota Depok, Jawa Barat 16411"
            ),
        )

        adapter = BankSampahAdapter(valuePlace)
        binding.rvTempat.layoutManager = LinearLayoutManager(context)
        binding.rvTempat.adapter = adapter

        binding.hyperlinkTextView.setOnClickListener {
            val url = "https://waste4change.com/"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}