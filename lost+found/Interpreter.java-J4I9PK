import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

/**
 * An implementation of the Interpreter class as requested per mission 1.
 */
public class Interpreter implements InterpreterInterface {
    /** the underlying operand stack */
    private Stack<Element<?>> stack = new LinkedStack<Element<?>>();
    /** the symbol table used to lookup names */
    private Map<String, Element<?>> symb = new HashMap<String, Element<?>>();
    
    /**
     * {@inheritDoc}
     *
     * The main functionality of the interpreter: returns a string to be printed
     * iff pstack is one of the operands.
     *
     * @param s: the string to return
     * @return a string representing the content of the stack (if need be).
     * 	otherwise, returns an empty string.
     * ----------
     * @pre: 'instructions' is a valid chain of PostScript instructions
     * @post: returns a String representing the state of the stack when a 'pstack' instruction is encountered.
     *    If several 'pstack' instructions are present in the chain, a concatenation of the corresponding states (when 'pstack' is encountered) must be returned, separated by whitespaces.
     *    If several elements are still on the stack, separate them with whitespaces.
     *    If there is no element on the stack or no 'pstack' instruction, return the empty string ("").
     */
    public String interpret(String s){
        String r = "";
        for(String tok : s.split(" ")){
            // Skip empty tokens to respect preconds.
            if(tok.equals(""))
                continue;
            r += ( " "+token(tok).execute() );
            r = r.trim();
        }
        return r;
    }
    
    /**
     * A factory method that maps an action to each of the corresponding tokens.
     *
     * @param token the token whose action is being looked up.
     * @return a TokenAction corresponding to whatever needs to be done when this
     * 	token is being processed.
     *
     * @pre: the current token is not null or "".
     * @post: an appropriate tokenaction is returned or an IllegalArgumentException
     * 	(unrecognised token) is raised.
     */
    private TokenAction token(String token){
        // Values -- Double
        if(token.matches("-?[0-9]+\\.[0-9]+"))
            return ()-> stackvalue(Double::parseDouble, token);
        // Values -- Int
        if(token.matches("-?[0-9]+"))
            return ()-> stackvalue(Integer::parseInt, token);
        // Values -- Boolean
        if(token.matches("true|false"))
            return ()-> stackvalue(Boolean::parseBoolean, token);
        
        // Arithmetic
        if(token.equals("add"))
            return () -> stackply(this::add, stack.pop(), stack.pop());
        if(token.equals("sub"))
            return ()-> stackply(this::sub, stack.pop(), stack.pop());
        
        if(token.equals("mul"))
            return ()-> stackply(this::mul, stack.pop(), stack.pop());
        if(token.equals("div"))
            return ()-> stackply(this::div, stack.pop(), stack.pop());

        if(token.equals("idiv"))
            return ()-> stackply(this::idiv, stack.pop(), stack.pop());
        
        // Logic
        if(token.equals("eq"))
            return ()-> stackply(this::eq, stack.pop(), stack.pop());
        if(token.equals("ne"))
            return ()-> stackply(this::ne, stack.pop(), stack.pop());
        
        // Stack manip
        if(token.equals("dup"))
            return ()-> { stack.push(stack.peek()); return "";};
        
        if(token.equals("exch"))
            return () -> {
                Element<?> a = stack.pop();
                Element<?> b = stack.pop();
                stack.push(a);
                stack.push(b);
                
                return "";
            };
        
        if(token.equals("pop"))
            return ()-> { stack.pop(); return "";};
        
        // IO
        if(token.equals("pstack"))
            return () -> {
                StringBuilder sb = new StringBuilder();
                
                Stack<Element<?>> reversed = new LinkedStack<Element<?>>();
                for(Element<?> e : stack){ reversed.push(e); }
                
                for(Element<?> e : reversed){
                    sb.append(e.value.toString()).append(" ");
                }
                return sb.toString().trim();
            };
        
        // Symbols
        if(token.equals("def"))
            return () -> {
                Element<?> v = stack.pop();
                String k     = ((String) stack.pop().value).substring(1);
                
                symb.put(k, v);
                return "";
            };
        
        if(token.startsWith("/"))
            return () -> { stack.push(new Element<String>(token)); return "";};
        
        
        if(symb.containsKey(token))
            return () -> { stack.push(symb.get(token)); return "";};
        
        throw new IllegalArgumentException("unrecognised token");
    }
    
    /**
     * Swaps the arguments a and b and passes them on to the given infix binary
     * operator so as to produce some result. Then pushes that result onto the
     * stack.
     *
     * IMPORTANT NOTICE:
     * Swapping the arguments in this function isn't just for fun, it stemps from
     * the fact that operands are popped from the stack and hence are in reverse
     * order wrt. the order of the formal parameters of the operators.
     *
     * @return the empty string (ALWAYS).
     */
    private String stackply(Operator op, Element<?> a, Element<?> b){
        stack.push(op.apply(b,a));
        return "";
    }
    
    /**
     * Uses the given function `fun` to parse the token and make it an element
     * which is then pushed onto the stack.
     *
     * @return the empty string (ALWAYS).
     */
    private <T> String stackvalue(Function<String, T> fun, String token){
        stack.push(new Element<T>(fun.apply(token)));
        return "";
    }
    
    /**
     * A functional interface to represent the action undertaken for each token
     * encountered while processing the stream of actions.
     */
    private interface TokenAction {
        /**
         * Provokes whatever side effect is required for some given token and
         * returns a string representation of the result if need be.
         *
         * @return a string representation of the result of the action iff that
         * 	action involves the printing of some content on the output stream
         *  (that is to say, iff the token is 'pstack').
         *
         * @pre: the current token is not null or "".
         * @post: a side effect happened (stack manipulation: push, pop, ...)
         * @post: a string representation of the result is returned (if need be)
         * 	else the empty string is returned.
         */
        public String execute();
    }
    
    /** A functional interface to encapsulate a binary operator */
    private interface Operator {
        /**
         * applies this operator to params a and b and yields the resulting
         * element.
         */
        Element<?> apply(Element<?> a, Element<?> b);
    }
    
    /**
     * Implements the binary addition operation (in infix notation).
     * The resulting element will be an Element<Integer> iff a and b are
     * Element<Integer>. Otherwise, if one or both arguments are floats, returns
     * an Element<Double>.
     *
     * In the (unlikely) event where a or b wouldnt be a number, an
     * IllegalArgumentException is raised.
     */
    public Element<?> add(Element<?> a, Element<?> b){
        if(a.isFloat() || b.isFloat())
            return new Element<Double>(a.fVal() + b.fVal());
        if(a.isInt()  && b.isInt())
            return new Element<Integer>(a.iVal() + b.iVal());
        
        throw new UnsupportedOperationException();
    }
    /**
     * Implements the binary subtraction operation (in infix notation).
     * The resulting element will be an Element<Integer> iff a and b are
     * Element<Integer>. Otherwise, if one or both arguments are floats, returns
     * an Element<Double>.
     *
     * In the (unlikely) event where a or b wouldnt be a number, an
     * IllegalArgumentException is raised.
     */
    public Element<?> sub(Element<?> a, Element<?> b){
        if(a.isFloat() || b.isFloat())
            return new Element<Double>(a.fVal() - b.fVal());
        if(a.isInt()  && b.isInt())
            return new Element<Integer>(a.iVal() - b.iVal());
        
        throw new UnsupportedOperationException();
    }
    /**
     * Implements the binary multiplication operation (in infix notation).
     * The resulting element will be an Element<Integer> iff a and b are
     * Element<Integer>. Otherwise, if one or both arguments are floats, returns
     * an Element<Double>.
     *
     * In the (unlikely) event where a or b wouldnt be a number, an
     * IllegalArgumentException is raised.
     */
    public Element<?> mul(Element<?> a, Element<?> b){
        if(a.isFloat() || b.isFloat())
            return new Element<Double>(a.fVal() * b.fVal());
        if(a.isInt()  && b.isInt())
            return new Element<Integer>(a.iVal() * b.iVal());
        
        throw new UnsupportedOperationException();
    }
    /**
     * Implements the binary division operation (in infix notation).
     * The resulting element Element<Double>.
     *
     * Anyhow, the resulting value will ALWAYS be a float (double) value.
     *
     * In the (unlikely) event where a or b wouldnt be a number, an
     * IllegalArgumentException is raised.
     */
    public Element<Double> div(Element<?> a, Element<?> b){
        if(a.isNumeric() && b.isNumeric())
            if(b.fVal() == 0.0)
                throw new ArithmeticException("Integer division by zero");
            else
                return new Element<Double>(a.fVal() / b.fVal());
        
        throw new UnsupportedOperationException();
    }

    /**
     * Implements the binary integer division operation (in infix notation).
     * The resulting element will be an Element<Integer> iff a and b are
     * Element<Integer>. Otherwise, if one or both arguments are floats, returns
     * an Element<Double>.
     *
     * Anyhow, the resulting value will ALWAYS be a float (double) value.
     *
     * In the (unlikely) event where a or b wouldnt be a number, an
     * IllegalArgumentException is raised.
     */
    public Element<Integer> idiv(Element<?> a, Element<?> b){
        if(a.isInt() && b.isInt())
            if(b.iVal() == 0)
                throw new ArithmeticException("Integer division by zero");
            else
                return new Element<Integer>(a.iVal() / b.iVal());

        throw new UnsupportedOperationException();
    }

    /** Implements a binary equality test operation */
    public Element<?> eq(Element<?> a, Element<?> b){
        return new Element<Boolean>(a.value.equals(b.value));
    }
    /** Implements a binary inequality test operation */
    public Element<?> ne(Element<?> a, Element<?> b){
        return new Element<Boolean>(!a.value.equals(b.value));
    }
    
    /**
     * Utility class meant to let other apis query the internal type of the
     * value in a relatively clean fashion. (User only needs to care about the
     * fact that the given element isInt or isFloat but does not need to perform
     * the bulk of the tests himself).
     */
    private class Element<T> {
        /** the value of the element */
        public final T value;
        /** creates a new instance that wraps the given v */
        private Element(T v){
            this.value = v;
        }
        /** @return true iff value is an integer number */
        public boolean isInt() {
            return value instanceof Integer;
        }
        /** @return true iff value is a floating point number (double) */
        public boolean isFloat(){
            return value instanceof Double;
        }
        /** @return true iff value is a number (int or floating point) */
        public boolean isNumeric(){
            return isInt() || isFloat();
        }
        
        /**
         * @return the value of this object expressed as a floating point number
         * 	(if possible).
         */
        private double  fVal(){ return ((Number) value).doubleValue();}
        /**
         * @return the value of this object expressed as an integer number
         * 	(if possible).
         */
        private int     iVal(){ return ((Number) value).intValue();}
        /**
         * {@inheritDoc}
         * Makes debugging easier.
         */
        public String toString() { return ""+value;}
    }
    
    /** The stack interface corresponding to the Stack ADT. */
    public interface Stack<T> extends Iterable<T> {
        void push(T t);
        T pop();
        T peek();
        boolean isEmpty();
        Iterator<T> iterator();
    }
    
    /** An implementation of the stack interface relying on a singly linked list */
    private class LinkedStack<T> implements Stack<T> {
        /** the head of the list. Null when the stack is empty */
        private Node top;
        
        /** {@inheritDoc} */
        public void push(T t){
            this.top = new Node(t, top);
        }
        /** {@inheritDoc} */
        public T pop(){
            T t = peek();
            this.top = this.top.next;
            return t;
        }
        /** {@inheritDoc} */
        public T peek(){
            if(isEmpty()){
                throw new EmptyStackException();
            }
            return this.top.value;
        }
        /** {@inheritDoc} */
        public boolean isEmpty(){
            return this.top == null;
        }
        /** {@inheritDoc} */
        public Iterator<T> iterator(){
            return new LnkStackIterator(); 
        }
        
        /** 
         * The class implementing the singly linked nodes of the singly linked
         * list data structure.
         */
        private class Node {
            T        value;
            Node     next;
            
            public Node(T value, Node next){
                this.value = value;
                this.next  = next;
            }
        }
        /**
         * An iterator that permits to iterate over all the elements contained
         * in the LinkedStack stack.
         */
        private class LnkStackIterator implements Iterator<T>{
            /** The node at which the cursor is set */
            private Node current;
            /** creates a new instance */
            public LnkStackIterator() {
                current = LinkedStack.this.top;
            }
            /** {@inheritDoc} */
            @Override
            public boolean hasNext() {
                return current != null;
            }
            /** {@inheritDoc} */
            @Override
            public T next() {
                T ret   = current.value;
                current = current.next;
                return ret;
            }
            
        }
    }
    
}
