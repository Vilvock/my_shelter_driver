package br.com.app5m.appshelterdriver.util

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import br.com.app5m.appshelterdriver.util.visual.Animation
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Validation (context: Context) {

    private val animation = Animation(context)

    private fun validatorCNPJ(CNPJ: String): Boolean {
        if (CNPJ == "00000000000000" || CNPJ == "11111111111111" || CNPJ == "22222222222222" || CNPJ == "33333333333333" || CNPJ == "44444444444444" || CNPJ == "55555555555555" || CNPJ == "66666666666666" || CNPJ == "77777777777777" || CNPJ == "88888888888888" || CNPJ == "99999999999999" || CNPJ.length != 14) return false
        val dig13: Char
        val dig14: Char
        var sm: Int
        var i: Int
        var r: Int
        var num: Int
        var peso: Int
        return try {
            sm = 0
            peso = 2
            i = 11
            while (i >= 0) {
                num = CNPJ[i].toInt() - 48
                sm += num * peso
                peso += 1
                if (peso == 10) peso = 2
                i--
            }
            r = sm % 11
            dig13 = if (r == 0 || r == 1) '0' else (11 - r + 48).toChar()
            sm = 0
            peso = 2
            i = 12
            while (i >= 0) {
                num = CNPJ[i].toInt() - 48
                sm += num * peso
                peso += 1
                if (peso == 10) peso = 2
                i--
            }
            r = sm % 11
            dig14 = if (r == 0 || r == 1) '0' else (11 - r + 48).toChar()
            dig13 == CNPJ[12] && dig14 == CNPJ[13]
        } catch (erro: InputMismatchException) {
            false
        }
    }

    private fun validatorCpf(CPF: String): Boolean {
        if (CPF == "00000000000" || CPF == "11111111111" || CPF == "22222222222" || CPF == "33333333333" || CPF == "44444444444" || CPF == "55555555555" || CPF == "66666666666" || CPF == "77777777777" || CPF == "88888888888" || CPF == "99999999999" || CPF.length != 11) return false
        val dig10: Char
        val dig11: Char
        var sm: Int
        var i: Int
        var r: Int
        var num: Int
        var peso: Int
        return try {
            sm = 0
            peso = 10
            i = 0
            while (i < 9) {
                num = CPF[i].toInt() - 48
                sm += num * peso
                peso -= 1
                i++
            }
            r = 11 - sm % 11
            dig10 = if (r == 10 || r == 11) '0' else (r + 48).toChar()
            sm = 0
            peso = 11
            i = 0
            while (i < 10) {
                num = CPF[i].toInt() - 48
                sm += num * peso
                peso -= 1
                i++
            }
            r = 11 - sm % 11
            dig11 = if (r == 10 || r == 11) '0' else (r + 48).toChar()
            dig10 == CPF[9] && dig11 == CPF[10]
        } catch (erro: InputMismatchException) {
            false
        }
    }

    fun phone(editText: EditText): Boolean {
        if (editText.text.toString().isEmpty()) {
            error(editText, "Telefone deve ser preenchido.")
            animation.shake(editText)
            return false
        }
        if (editText.text.toString().length < 15) {
            error(editText, "Telefone deve ser preenchido de forma completa.")
            animation.shake(editText)
            return false
        }
        return true
    }

    fun cellphone(editText: EditText): Boolean {
        if (editText.text.toString().isEmpty()) {
            error(editText, "Celular deve ser preenchido.")
            animation.shake(editText)
            return false
        }
        if (editText.text.toString().length < 15) {
            error(editText, "Celular deve ser preenchido de forma completa.")
            animation.shake(editText)
            return false
        }
        return true
    }

    private fun isEmpty(editText: EditText?): Boolean {
        if (editText == null) {
            return false
        }
        if (editText.hint == null) {
            return !editText.text.toString().isEmpty() || editText.text.toString().isNotEmpty()
        }
        if (editText.text.toString().isEmpty() && editText.text.toString().isEmpty()) {
            error(editText, editText.hint.toString() + " deve ser preenchido(a).")
            return false
        }
        return true
    }


    fun password(editText: EditText, type: Int): Boolean {
        if (editText.text.toString().isEmpty()) {
            error(editText, "Senha deve ser preenchida.")
            animation.shake(editText)
            return false
        }

        if (type == 0) {
            if (editText.text.length < 8) {
                error(editText, "Senha deve ter no mínimo 8 digitos.")
                animation.shake(editText)
                return false
            }
        }

        return true
    }

    /**
     * Caso o cpf não seja valido ele retorna falso
     */
    fun cpf(editText: EditText): Boolean {
        if (editText.text.toString().isEmpty()) {
            error(editText, "CPF deve ser preenchido.")
            animation.shake(editText)
            return false
        }
        if (editText.text.length < 11) {
            error(editText, "CPF deve ser preenchido de forma completa.")
            animation.shake(editText)
            return false
        }
        if (!validatorCpf(editText.text.toString().replace(".", "").replace("-", ""))) {
            error(editText, "Este CPF não é válido.")
            animation.shake(editText)
            return false
        }
        return true
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) false else Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun cnpj(editText: EditText): Boolean {
        if (editText.text.toString().isEmpty()) {
            error(editText, "CNPJ deve ser preenchido.")
            animation.shake(editText)
            return false
        }
        if (editText.text.length < 14) {
            error(editText, "CNPJ deve ser preenchido de forma completa.")
            animation.shake(editText)
            return false
        }
        if (!validatorCNPJ(editText.text.toString().replace(".", "")
                .replace("-", "").replace("/", ""))) {
            error(editText, "Este CNPJ não é válido.")
            animation.shake(editText)
            return false
        }
        return true
    }

    fun date(editText: EditText): Boolean {

        if (editText.text.toString().isEmpty()) {
            editText.error = "Data precisa ser preenchida"
            animation.shake(editText)
            return false
        }

        if (editText.text.toString().length < 10) {
            editText.error = "Data inválida"
            animation.shake(editText)
            return false
        }

        if (!checkDPP(editText.text.toString())) {
            editText.error = "Data inválida"
            animation.shake(editText)
            return false
        }

        return true
    }

    private fun checkDPP(string: String): Boolean {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val dateA = Date()
        var dateB: Date? = null
        try {
            dateB = simpleDateFormat.parse(string)
            Log.d("data", "checkDPP: ")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return if (dateB == null) false else dateA.after(dateB)
    }

    /**
     * Se o nome for valido ele retorna verdadeiro
     */
    fun name(editText: EditText): Boolean {
        if (editText.text.toString().isEmpty()) {
            error(editText, "Nome deve ser preenchido.")
            animation.shake(editText)
            return false
        }

        return true
    }

    fun lastName(editText: EditText): Boolean {
        if (editText.text.toString().isEmpty()) {
            error(editText, "Sobrenome deve ser preenchido.")
            animation.shake(editText)
            return false
        }

        return true
    }

    fun email(editText: EditText): Boolean {
        if (editText.text.toString().isEmpty()) {
            error(editText, "E-mail deve ser preenchido.")
            animation.shake(editText)
            return false
        }
        if (!isValidEmail(editText.text.toString())) {
            error(editText, "E-mail deve ser preenchido em um formato válido.")
            animation.shake(editText)
            return false
        }
        return true
    }

    fun coPassword(pass1: EditText, pass2: EditText): Boolean {
        if (pass1.text.toString() != pass2.text.toString()) {
            error(pass2, "Senhas não coincidem.")
            animation.shake(pass2)
            return false
        }
        return true
    }

    private fun error(editText: EditText?, text: String) {
        if (editText == null) {
            return
        }
        editText.error = text
        editText.requestFocus()
    }

    fun validateTextField(editText: EditText): Boolean {
        if (!isEmpty(editText) || editText.error != null) {
            animation.shake(editText)
            error(editText, "Este campo deve ser preenchido!")
            return false
        }
        return true
    }

}