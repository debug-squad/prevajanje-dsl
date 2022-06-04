package task

import task.lexer.EvalAutomaton
import task.lexer.Scanner
import task.lexer.TokenType
import java.io.File
import java.io.FileInputStream

fun printTokens(scanner: Scanner) {
    while (true) {
        val token = scanner.getToken() ?: break
        print("${TokenType.name(token.value)}(\"${token.lexeme}\") ")
    }
}

fun main(args: Array<String>) {
    val scanner = Scanner(EvalAutomaton, FileInputStream(File(args[0])))
    val parser = Parser(scanner)
    println("${if(parser.parse()) { "accept" } else { "reject" }}")
    printTokens(scanner)
}