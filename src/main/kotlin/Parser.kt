package task

import task.lexer.Scanner
import task.lexer.TokenType.*

class Parser(private val scanner: Scanner) {
    private var token = scanner.getToken()

    fun parse(): Boolean {
        return STATEMENTS() && scanner.eof()
    }

    fun STATEMENTS(): Boolean {
        if (!STATEMENT()) return false
        while (token?.value == SEMI.value) {
            token = scanner.getToken()
            if (!STATEMENT()) return false
        }
        return true
    }

    fun STATEMENT(): Boolean {
        when (token?.value) {
            COLON.value -> {
                token = scanner.getToken()
                if (token?.value != VARIABLE.value) return false
                token = scanner.getToken()
                val last = token
                if (!EXPRESSION()) {
                    if (token != last) return false
                }
                if (!LAMBDA_BODY()) return false
                return true
            }
            FN.value -> {
                token = scanner.getToken()
                if (token?.value != VARIABLE.value) return false
                token = scanner.getToken()
                if (token?.value != LPAREN.value) return false
                token = scanner.getToken()
                if (token?.value == VARIABLE.value) {
                    token = scanner.getToken()
                    while (token?.value == COMMA.value) {
                        token = scanner.getToken()
                        if (token?.value != VARIABLE.value) return false
                        token = scanner.getToken()
                    }
                }
                if (token?.value != RPAREN.value) return false
                if (!LAMBDA_BODY()) return false
                return true
            }
            LBR.value -> {
                token = scanner.getToken()
                if (!STATEMENTS()) return false
                if (token?.value != RBR.value) return false
                token = scanner.getToken()
                return true
            }
            IF.value -> {
                token = scanner.getToken()
                if (!EXPRESSION()) return false
                if (token?.value != LBR.value) return false
                token = scanner.getToken()
                if (!STATEMENTS()) return false
                if (token?.value != RBR.value) return false
                token = scanner.getToken()
                if (token?.value == ELSE.value) {
                    token = scanner.getToken()
                    if (token?.value != LBR.value) return false
                    token = scanner.getToken()
                    if (!STATEMENTS()) return false
                    if (token?.value != RBR.value) return false
                    token = scanner.getToken()
                }
                return true
            }
            FOR.value -> {
                token = scanner.getToken()
                if (token?.value != VARIABLE.value) return false
                token = scanner.getToken()
                if (token?.value != IN.value) return false
                token = scanner.getToken()
                if (!EXPRESSION()) return false
                if (token?.value != LBR.value) return false
                token = scanner.getToken()
                if (!STATEMENTS()) return false
                if (token?.value != RBR.value) return false
                token = scanner.getToken()
                return true
            }
            LET.value -> {
                token = scanner.getToken()
                if (token?.value != VARIABLE.value) return false
                token = scanner.getToken()
                if (token?.value == ASSIGN.value) {
                    token = scanner.getToken()
                    if (!EXPRESSION()) return false
                    return true
                }
                return true
            }
            VARIABLE.value -> {
                token = scanner.getToken()
                when (token?.value) {
                    ASSIGN.value -> {
                        token = scanner.getToken()
                        if (!EXPRESSION()) return false
                        return true
                    }
                    LPAREN.value -> {
                        token = scanner.getToken()
                        val last = token
                        if (!EXPRESSION()) {
                            if (token != last) return false
                        } else {
                            while (token?.value == COMMA.value) {
                                token = scanner.getToken()
                                if (!EXPRESSION()) return false
                            }
                        }
                        if (token?.value != RPAREN.value) return false
                        token = scanner.getToken()
                        return true
                    }
                    else -> return false
                }
            }
            RETURN.value -> {
                token = scanner.getToken()
                val last = token
                if (!EXPRESSION()) {
                    if (token != last) return false
                    return true
                }
                return true
            }
            CONTINUE.value -> {
                token = scanner.getToken()
                return true
            }
            BREAK.value -> {
                token = scanner.getToken()
                return true
            }
            LINE.value -> {
                token = scanner.getToken()
                if (!EXPRESSION()) return false
                while (token?.value == LINE.value) {
                    token = scanner.getToken()
                    if (!EXPRESSION()) return false
                    if (token?.value == VARIABLE.value) {
                        token = scanner.getToken()
                        if (token?.value == LPAREN.value) {
                            token = scanner.getToken()
                            val last = token
                            if (!EXPRESSION()) {
                                if (token != last) return false
                            } else {
                                while (token?.value == COMMA.value) {
                                    token = scanner.getToken()
                                    if (!EXPRESSION()) return false
                                }
                            }
                            if (token?.value != RPAREN.value) return false
                            token = scanner.getToken()
                        }
                    }
                }
                return true
            }
            else -> return true
        }
    }

    //
    //
    //

    fun EXPRESSION(): Boolean = EXPR_08()

    fun EXPR_08(): Boolean {
        if (!EXPR_07()) return false
        while (token?.value == OR.value) {
            token = scanner.getToken()
            if (!EXPR_07()) return false
        }
        return true
    }

    fun EXPR_07(): Boolean {
        if (!EXPR_06()) return false
        while (token?.value == AND.value) {
            token = scanner.getToken()
            if (!EXPR_06()) return false
        }
        return true
    }

    fun EXPR_06(): Boolean {
        if (!EXPR_05()) return false
        when (token?.value) {
            EQ.value -> {
                token = scanner.getToken()
                if (!EXPR_05()) return false
                return true
            }
            NOTEQ.value -> {
                token = scanner.getToken()
                if (!EXPR_05()) return false
                return true
            }
            else -> return true
        }
    }

    fun EXPR_05(): Boolean {
        if (!EXPR_04()) return false
        when (token?.value) {
            MORE.value -> {
                token = scanner.getToken()
                if (!EXPR_04()) return false
                return true
            }
            MOREEQ.value -> {
                token = scanner.getToken()
                if (!EXPR_04()) return false
                return true
            }
            LESS.value -> {
                token = scanner.getToken()
                if (!EXPR_04()) return false
                return true
            }
            LESSEQ.value -> {
                token = scanner.getToken()
                if (!EXPR_04()) return false
                return true
            }
            else -> return true
        }
    }

    fun EXPR_04(): Boolean {
        if (!EXPR_03()) return false
        while (true) {
            when (token?.value) {
                PLUS.value -> {
                    token = scanner.getToken()
                    if (!EXPR_03()) return false
                }
                MINUS.value -> {
                    token = scanner.getToken()
                    if (!EXPR_03()) return false
                }
                else -> return true
            }
        }
    }

    fun EXPR_03(): Boolean {
        if (!EXPR_02()) return false
        while (true) {
            when (token?.value) {
                TIMES.value -> {
                    token = scanner.getToken()
                    if (!EXPR_02()) return false
                }
                DIVIDE.value -> {
                    token = scanner.getToken()
                    if (!EXPR_02()) return false
                }
                MOD.value -> {
                    token = scanner.getToken()
                    if (!EXPR_02()) return false
                }
                else -> return true
            }
        }
    }

    fun EXPR_02(): Boolean {
        if (!EXPR_01()) return false
        while (token?.value == POW.value) {
            token = scanner.getToken()
            if (!EXPR_01()) return false
        }
        return true
    }

    fun EXPR_01(): Boolean {
        when (token?.value) {
            PLUS.value -> {
                token = scanner.getToken()
                if (!EXPR_00()) return false
                return true
            }
            MINUS.value -> {
                token = scanner.getToken()
                if (!EXPR_00()) return false
                return true
            }
            NOT.value -> {
                token = scanner.getToken()
                if (!EXPR_00()) return false
                return true
            }
            else -> {
                if (!EXPR_00()) return false
                return true
            }
        }
    }

    fun EXPR_00(): Boolean {
        when (token?.value) {
            VARIABLE.value -> {
                token = scanner.getToken()
                if (token?.value == LPAREN.value) {
                    token = scanner.getToken()
                    val last = token
                    if (!EXPRESSION()) {
                        if (token != last) return false
                    } else {
                        while (token?.value == COMMA.value) {
                            token = scanner.getToken()
                            if (!EXPRESSION()) return false
                        }
                    }
                    if (token?.value != RPAREN.value) return false
                    token = scanner.getToken()
                    return true
                }
                return true
            }
            LPAREN.value -> {
                token = scanner.getToken()
                if (!EXPRESSION()) return false
                if (token?.value == COMMA.value) {
                    token = scanner.getToken()
                    if (!EXPRESSION()) return false
                    if (token?.value != RPAREN.value) return false
                    token = scanner.getToken()
                    return true
                }
                if (token?.value != RPAREN.value) return false
                token = scanner.getToken()
                return true
            }
            NULL.value -> {
                token = scanner.getToken()
                return true
            }
            FLOAT.value -> {
                token = scanner.getToken()
                return true
            }
            STR0.value -> {
                token = scanner.getToken()
                return true
            }
            STR1.value -> {
                token = scanner.getToken()
                return true
            }
            STR2.value -> {
                token = scanner.getToken()
                return true
            }
            TRUE.value -> {
                token = scanner.getToken()
                return true
            }
            FALSE.value -> {
                token = scanner.getToken()
                return true
            }
            FN.value -> {
                token = scanner.getToken()
                if (token?.value != LPAREN.value) return false
                token = scanner.getToken()

                var args = mutableSetOf<String>()
                if (token?.value == VARIABLE.value) {
                    token = scanner.getToken()
                    while (token?.value == COMMA.value) {
                        token = scanner.getToken()
                        if (token?.value != VARIABLE.value) return false
                        token = scanner.getToken()
                    }
                }
                if (token?.value != RPAREN.value) return false
                if (!LAMBDA_BODY()) return false
                return true
            }
            LSQBR.value -> {
                token = scanner.getToken()
                val last = token
                if (!EXPRESSION()) {
                    if (token != last) return false
                } else {
                    while (token?.value == COMMA.value) {
                        token = scanner.getToken()
                        if (!EXPRESSION()) return false
                    }
                }
                if (token?.value != RSQBR.value) return false
                token = scanner.getToken()
                return true
            }
            else -> return false
        }
    }

    fun LAMBDA_BODY(): Boolean {
        when (token?.value) {
            LBR.value -> {
                token = scanner.getToken()
                if (!STATEMENTS()) return false
                if (token?.value != RBR.value) return false
                token = scanner.getToken()
                return true
            }
            ASSIGN.value -> {
                token = scanner.getToken()
                if (!EXPRESSION()) return false
                return true
            }
            else -> return false
        }
    }
}