package io.getquill

import miniquill.context.mirror._
import io.getquill.context._

class MirrorContext[Dialect <: io.getquill.idiom.Idiom, Naming <: io.getquill.NamingStrategy](val idiom: Dialect, val naming: Naming)
extends Context[Dialect, Naming] 
with MirrorDecoders {
  override type Result[T] = T
  override type RunQueryResult[T] = QueryMirror[T]

  case class QueryMirror[T](string: String, prepareRow: PrepareRow, extractor: Extractor[T]) {
    def string(pretty: Boolean): String =
      if (pretty)
        idiom.format(string)
      else
        string
  }

  //prepare: Prepare = identityPrepare, 
  def executeQuery[T](string: String, extractor: Extractor[T] = identityExtractor) =
    QueryMirror(string, null, extractor)
    //QueryMirror(string, prepare(Row())._2, extractor)
}