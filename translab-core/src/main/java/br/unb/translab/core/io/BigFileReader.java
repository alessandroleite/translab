package br.unb.translab.core.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

public class BigFileReader implements Iterable<String>, Closeable
{
    private static final transient Logger LOGGER = LoggerFactory.getLogger(BigFileReader.class);
    
    /**
     * The buffer to read the file.
     */
    private final BufferedReader _reader;

    /**
     * A reference to the {@link File}.
     */
    private final File _file;

    /**
     * A flag to indicate that the file must be closed when reached the end.
     */
    private final boolean _autocloseable;

    /**
     * The file iterator.
     */
    private final Iterator<String> _interator;

    /**
     * Creates a new {@link BigFileReader} instance by converting the given pathname string into an abstract pathname.
     * 
     * @param filePath
     *            A pathname of the file to be read.
     * @throws IOException
     *             If the file does not exist.
     */
    public BigFileReader(final String filePath) throws IOException
    {
        this(new File(filePath));
    }

    /**
     * 
     * Creates a new {@link BigFileReader} instance by converting the given pathname string into an abstract pathname.
     * 
     * @param filePath
     *            A pathname of the file to be read.
     * @param closeOnEnd
     *            A flat to indicates if the file must be closed when reached the end.
     * @throws IOException
     *             If the file does not exist.
     */
    public BigFileReader(final String filePath, boolean closeOnEnd) throws IOException
    {
        this(new File(filePath), closeOnEnd);
    }

    /**
     * Creates a new {@link BigFileReader} instance to read the given {@link File}.
     * 
     * @param file
     *            The file to be read. Might not be <code>null</code>.
     * @throws IOException
     *             If the file does not exist.
     */
    public BigFileReader(File file) throws IOException
    {
        this(file, true);
    }

    /**
     * Creates a new {@link BigFileReader} instance to read the given {@link File}.
     * 
     * @param file
     *            The file to be read. Might not be <code>null</code>.
     * @param closeOnEnd
     *            A flag to indicate that the file must be closed when reached the end.
     * @throws IOException
     *             If the file does not exist.
     */
    public BigFileReader(File file, boolean closeOnEnd) throws IOException
    {
        this._file = Preconditions.checkNotNull(file);
        _reader = new BufferedReader(new FileReader(file));
        this._autocloseable = closeOnEnd;
        this._interator = new FileIterator();
    }

    /**
     * Count the number of lines. It does not move the cursor.
     * 
     * @return The number of lines in the file.
     * @throws IOException
     *             If file does not exists.
     */
    public int count() throws IOException
    {
        int cont = 0;
        
        try (BigFileReader other = new BigFileReader(this._file))
        {
            while (other.iterator().hasNext())
            {
                cont++;
            }
        }

        return cont;
    }
    
    /**
     * Skips one line in the file iff there is one, and returns the content of the skipped line.
     * 
     * @return the value of the skipped line or {@link Optional#absent()} if there isn't one line to skip.
     */
    public Optional<String> skip()
    {
        Optional<String> value = Optional.absent();
        
        if (this._interator.hasNext())
        {
            value = Optional.fromNullable(this._interator.next());
        }
        
        return value;
    }

    @Override
    public void close() throws IOException
    {
        _reader.close();
    }

    @Override
    public Iterator<String> iterator()
    {
        return this._interator;
    }

    private final class FileIterator implements Iterator<String>
    {
        /**
         * The value of the current line.
         */
        private String currentLine_;

        @Override
        public boolean hasNext()
        {
            try
            {
                currentLine_ = _reader.readLine();
            }
            catch (IOException exception)
            {
                currentLine_ = null;
            }
            finally
            {
                if (currentLine_ == null && _autocloseable)
                {
                    try
                    {
                        close();
                    }
                    catch (IOException ignore)
                    {
                       LOGGER.warn("Error on closing this file", ignore.getMessage());
                    }
                }
            }

            return currentLine_ != null;
        }

        @Override
        public String next()
        {
            return currentLine_;
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    protected void finalize() throws Throwable
    {
        this.close();
        super.finalize();
    }
}
