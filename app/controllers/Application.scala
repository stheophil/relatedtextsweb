package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.libs.json._

import net.theophil.relatedtexts._

object Application extends Controller {

	case class InputStatement(id: Long, title: String, override val text: String, override val keywords: Seq[String]) extends Analyzable
	
	val inputStatementForm = Form(
		tuple(
			"id" -> number,
			"title" -> text,
			"text" -> text,
			"keywords" -> seq(text)
		) 
	)
	def testTextAnalyzer = Action { implicit request =>
		Logger.info(request.headers.toString)
		Logger.info(request.body.toString)
		inputStatementForm.bindFromRequest.fold(
			formWithErrors => BadRequest(""),
			{ case (id, title, text, keywords) => {
				val stmt = InputStatement(id, title, text, keywords)
				Logger.info(id + ", " + title + ", " + text + ", " + keywords.mkString("/"))
				val analyzer = TextMatcher.preprocess( Seq(stmt) )
				val stats = analyzer.statistics.head._2

				Ok(Json.toJson(stats))
			}}
		)
	}	
}
