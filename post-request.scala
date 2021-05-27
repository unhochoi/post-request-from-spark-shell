import org.apache.http.impl.client.HttpClients
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.util.EntityUtils
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonDSL._

val lr = 97366
val lc = 33288
val rc = 5958
val ld = 0.00056263
val rd = 0.13
val lnnz  = 1823595
val rnnz  = 25785518
val lr_lc  = lr*lc
val lc_rc = lc*rc
val lr_rc = lr*rc
val lr_lc_rc = lr*lc*rc
val ld_rd  = ld*rd
val lr_rc_ld_rd = lr*rc*ld*rd
val lr_lc_rc_ld_rd  = lr*lc*rc*ld*rd
val lnnz_rnnz  = lnnz*rnnz

val apiurl = ""

val matrix_info : Map[String,org.json4s.JsonAST.JValue] = Map("lr"->lr, "lc"->lc, "rc"->rc, "ld"->ld, "rd"->rd, "lnnz"->lnnz, "rnnz"->rnnz, "lr*lc"-> lr_lc, "lc*rc"-> lc_rc, "lr*rc"-> lr_rc,"lr*lc*rc"-> lr_lc_rc, "ld*rd"-> ld_rd,  "lr*rc*ld*rd"-> lr_rc_ld_rd,"lr*lc*rc*ld*rd"-> lr_lc_rc_ld_rd, "lnnz*rnnz"-> lnnz_rnnz)

val json = pretty(render(matrix_info))

val client = HttpClients.createDefault()

val post : HttpPost = new HttpPost(apiurl)
post.addHeader("Content-Type", "application/json")
post.setEntity(new StringEntity(json))

val response : CloseableHttpResponse = client.execute(post)
val entity = response.getEntity
val response_str = EntityUtils.toString(entity,"UTF-8")

val Pattern = "[a-zA-Z_]+".r
val matches = Pattern.findAllIn(response_str.split(":")(2))
val optim_method = matches.toList(0)
