package com.example.calculadora

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.calculadora.databinding.ActivityMainBinding
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var ultimoResultadoFoiIgual = false // Variável de controle para saber se o último clique foi "igual"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val calculo = binding.calculo

        fun adicionarTexto(texto: String) {
            if (ultimoResultadoFoiIgual) {
                // Se o último clique foi "igual"
                if (texto in listOf("+", "-", "*", "/", ".")) {
                    // Se o próximo clique for um operador, adiciona o operador ao resultado atual
                    calculo.text = "${calculo.text}$texto"
                } else {
                    // Se o próximo clique for um número, ponto ou parêntese, substitui o resultado atual
                    calculo.text = texto
                }
                ultimoResultadoFoiIgual = false // Redefine o estado para permitir novas entradas
            } else {
                calculo.text = "${calculo.text}$texto" // Adiciona o texto ao cálculo atual
            }
        }

        // Configurações dos botões para adicionar números e operadores
        binding.um.setOnClickListener { adicionarTexto("1") }
        binding.dois.setOnClickListener { adicionarTexto("2") }
        binding.tres.setOnClickListener { adicionarTexto("3") }
        binding.quatro.setOnClickListener { adicionarTexto("4") }
        binding.cinco.setOnClickListener { adicionarTexto("5") }
        binding.seis.setOnClickListener { adicionarTexto("6") }
        binding.sete.setOnClickListener { adicionarTexto("7") }
        binding.oito.setOnClickListener { adicionarTexto("8") }
        binding.nove.setOnClickListener { adicionarTexto("9") }
        binding.zero.setOnClickListener { adicionarTexto("0") }
        binding.parentesesAbrindo.setOnClickListener { adicionarTexto("(") }
        binding.parentesesFechando.setOnClickListener { adicionarTexto(")") }
        binding.divisao.setOnClickListener { adicionarTexto("/") }
        binding.multiplicacao.setOnClickListener { adicionarTexto("*") }
        binding.subtracao.setOnClickListener { adicionarTexto("-") }
        binding.adicao.setOnClickListener { adicionarTexto("+") }
        binding.ponto.setOnClickListener { adicionarTexto(".") }

        binding.apagar.setOnClickListener {
            if (calculo.text.isNotEmpty()) {
                calculo.text = calculo.text.dropLast(1)
            }
        }

        binding.ce.setOnClickListener {
            calculo.text = ""
            binding.resultado.text = ""
            ultimoResultadoFoiIgual = false // Reseta a variável de controle
        }

        binding.igual.setOnClickListener {
            val resultadoCalculado = Expression(calculo.text.toString()).calculate()

            if (resultadoCalculado.isNaN()) {
                binding.resultado.text = "Expressão Inválida"
            } else {
                binding.resultado.text = resultadoCalculado.toString()
                calculo.text = resultadoCalculado.toString() // Atualiza o cálculo com o resultado
                ultimoResultadoFoiIgual = true // Marca que o último clique foi "igual"
            }
        }
    }
}
