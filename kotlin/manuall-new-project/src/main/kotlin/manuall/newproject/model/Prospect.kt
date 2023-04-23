package manuall.newproject.model

import java.util.Date
import jakarta.persistence.*

@Entity
@Table(name = "prospect")
class Prospect {

        @field:Id
        @field:GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Int = 0

        @Column(name = "id_cliente")
        var idCliente: Int = 0

        @Column(name = "nome", length = 60)
        var nome: String? = null

        @Column(name = "email", length = 256)
        var email: String? = null

        @Column(name = "fone", length = 13)
        var fone: String? = null

        @Column(name = "como_cobra", length = 45)
        var comoCobra: String? = null

        @Column(name = "dt_nascimento")
        var dtNascimento: Date? = null

        @Column(name = "opt_reside")
        var optReside: Int? = null

        @Column(name = "opt_tamanho")
        var optTamanho: Int? = null

        @Column(name = "opt_contratar")
        var optContratar: Int? = null

        @Column(name = "opt_buscando")
        var optBuscando: Int? = null

        @Column(name = "opt_experiencia")
        var optExperiencia: Int? = null

        @Column(name = "opt_canal")
        var optCanal: Int? = null

        @Column(name = "opt_interesse_loja")
        var optInteresseLoja: Int? = null

        @Column(name = "opt_interesse_plat")
        var optInteressePlat: Int? = null

        @Column(name = "bln_interesse_ensinar")
        var blnInteresseEnsinar: Boolean? = null

        @Column(name = "bln_ja_contratou")
        var blnJaContratou: Boolean? = null

        @Column(name = "bln_aprender")
        var blnAprender: Boolean? = null

        @Column(name = "bln_contratou")
        var blnContratou: Boolean? = null

        @Column(name = "bln_divulga")
        var blnDivulga: Boolean? = null

        @Column(name = "bln_divulgara")
        var blnDivulgara: Boolean? = null

        @Column(name = "msg_desistencia", length = 90)
        var msgDesistencia: String? = null

        @Column(name = "status")
        var status: Int? = null

        @Column(name = "tipo_usuario")
        var tipoUsuario: Int? = null
}