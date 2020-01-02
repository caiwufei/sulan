package com.yilanjiaju.log.common.base;

import org.apache.commons.io.output.ProxyOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;


public class ExtendHttpServletResponseWrapper extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
    private PrintWriter writer;

    public ExtendHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
        this.writer = new PrintWriter(this.bos);
    }

    @Override
    public ServletResponse getResponse() {
        return this;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStream() {
            /**
             * Checks if a non-blocking write will succeed. If this returns
             * <code>false</code>, it will cause a callback to
             * {@link WriteListener#onWritePossible()} when the buffer has emptied. If
             * this method returns <code>false</code> no further data must be written
             * until the contain calls {@link WriteListener#onWritePossible()}.
             *
             * @return <code>true</code> if data can be written, else <code>false</code>
             * @since Servlet 3.1
             */
            @Override
            public boolean isReady() {
                return false;
            }

            /**
             * Sets the {@link WriteListener} for this {@link ServletOutputStream} and
             * thereby switches to non-blocking IO. It is only valid to switch to
             * non-blocking IO within async processing or HTTP upgrade processing.
             *
             * @param listener The non-blocking IO write listener
             * @throws IllegalStateException If this method is called if neither
             *                               async nor HTTP upgrade is in progress or
             *                               if the {@link WriteListener} has already
             *                               been set
             * @throws NullPointerException  If listener is null
             * @since Servlet 3.1
             */
            @Override
            public void setWriteListener(WriteListener listener) {

            }

            private ExtendOutputStream tee;

            {
                this.tee = new ExtendOutputStream(ExtendHttpServletResponseWrapper.super.getOutputStream(), ExtendHttpServletResponseWrapper.this.bos);
            }

            @Override
            public void write(int b) throws IOException {
                this.tee.write(b);
            }
        };
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new ExtendPrintWriter(super.getWriter(), this.writer);
    }

    public byte[] toByteArray() {
        return this.bos.toByteArray();
    }
}

class ExtendOutputStream extends ProxyOutputStream {
    protected OutputStream branch;

    public ExtendOutputStream(OutputStream out, OutputStream branch) {
        super(out);
        this.branch = branch;
    }

    @Override
    public synchronized void write(byte[] b) throws IOException {
        super.write(b);
        this.branch.write(b);
    }

    @Override
    public synchronized void write(byte[] b, int off, int len) throws IOException {
        super.write(b, off, len);
        this.branch.write(b, off, len);
    }

    @Override
    public synchronized void write(int b) throws IOException {
        super.write(b);
        this.branch.write(b);
    }

    @Override
    public void flush() throws IOException {
        super.flush();
        this.branch.flush();
    }

    @Override
    public void close() throws IOException {
        try {
            super.close();
        } finally {
            this.branch.close();
        }

    }
}

class ExtendPrintWriter extends PrintWriter {
    PrintWriter branch;

    public ExtendPrintWriter(PrintWriter main, PrintWriter branch) {
        super(main, true);
        this.branch = branch;
    }

    @Override
    public void write(char[] buf, int off, int len) {
        super.write(buf, off, len);
        super.flush();
        this.branch.write(buf, off, len);
        this.branch.flush();
    }

    @Override
    public void write(String s, int off, int len) {
        super.write(s, off, len);
        super.flush();
        this.branch.write(s, off, len);
        this.branch.flush();
    }

    @Override
    public void write(int c) {
        super.write(c);
        super.flush();
        this.branch.write(c);
        this.branch.flush();
    }

    @Override
    public void flush() {
        super.flush();
        this.branch.flush();
    }
}
