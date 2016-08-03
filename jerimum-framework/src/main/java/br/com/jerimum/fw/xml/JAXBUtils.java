package br.com.jerimum.fw.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

/**
 * Classe utilitaria responsavel por operacoes de convercao XML/Object.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 */
public final class JAXBUtils {

    public static final String UTF_8 = "UTF-8";

    private JAXBUtils() {

    }

    /**
     * Retorna o esquema XSD de acordo com o path passado como parametro.
     * 
     * @param path
     * @return {@link Schema}
     * @throws URISyntaxException
     * @throws SAXException
     * @throws IOException
     */
    public static Schema getSchema(String path) throws URISyntaxException, SAXException, IOException {

        File fileSchema = getFile(path);
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        return sf.newSchema(fileSchema);
    }

    /**
     * Retorna o arquivo de acordo com o path passado como parametro.
     * 
     * @param path
     * @return {@link File}
     * @throws URISyntaxException
     * @throws IOException
     */
    public static File getFile(String path) throws URISyntaxException, IOException {

        if (StringUtils.startsWith(path, "file:")) {

            String auxPath = StringUtils.replace(path, " ", "%20");
            URL url = new URL(auxPath);
            return new File(url.toURI());

        } else {

            Resource schemaResource = new ClassPathResource(path);
            return schemaResource.getFile();
        }

    }

    /**
     * Realiza a operacao de conversao XML -> Object.
     * 
     * @param classe
     * @param schema
     * @param xmlFile
     * @return {@link Object}
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseToObject(Class<T> classe, Schema schema, File xmlFile) throws JAXBException {

        Unmarshaller unmarshaller = createUnmarshaller(classe, schema);
        return (T) unmarshaller.unmarshal(xmlFile);
    }

    /**
     * Realiza a operacao de conversao XML -> Object.
     * 
     * @param classe
     * @param xmlFile
     * @return {@link Object}
     * @throws JAXBException
     */
    public static <T> T parseToObject(Class<T> classe, File xmlFile) throws JAXBException {

        return parseToObject(classe, null, xmlFile);
    }

    /**
     * Realiza a operacao de conversao XML -> Object.
     * 
     * @param classe
     * @param schema
     * @param inputStream
     * @return
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseToObject(Class<T> classe, Schema schema, InputStream xml) throws JAXBException {

        Unmarshaller unmarshaller = createUnmarshaller(classe, schema);
        return (T) unmarshaller.unmarshal(xml);
    }

    /**
     * Realiza a operacao de conversao XML -> Object.
     * 
     * @param classe
     * @param inputStream
     * @return
     * @throws JAXBException
     */
    public static <T> T parseToObject(Class<T> classe, InputStream xml) throws JAXBException {

        return parseToObject(classe, null, xml);
    }

    /**
     * Realiza a operacao de conversao XML -> Object.
     * 
     * @param classe
     * @param schema
     * @param inputStream
     * @return
     * @throws JAXBException
     */
    public static <T> T parseToObject(Class<T> classe, Schema schema, String xml) throws JAXBException {

        return parseToObject(classe, schema, new ByteArrayInputStream(xml.getBytes(Charset.defaultCharset())));
    }

    /**
     * Realiza a operacao de conversao XML -> Object.
     * 
     * @param classe
     * @param inputStream
     * @return {@link Object}
     * @throws JAXBException
     */
    public static <T> T parseToObject(Class<T> classe, String xml) throws JAXBException {

        return parseToObject(classe, null, xml);
    }

    /**
     * 
     * @param classe
     * @param schema
     * @return {@link Unmarshaller}
     * @throws JAXBException
     */
    public static Unmarshaller createUnmarshaller(Class<?> classe, Schema schema) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(classe);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        if (schema != null) {
            unmarshaller.setSchema(schema);
        }

        return unmarshaller;
    }

    /**
     * 
     * @param classe
     * @param schema
     * @param formatXML
     * @return {@link Marshaller}
     * @throws JAXBException
     */
    public static Marshaller createMarshaller(Class<?> classe, Schema schema, boolean formatXML) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(classe);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatXML);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        // marshaller.setProperty(com.sun.xml.bind.marshaller.CharacterEscapeHandler.class.getName(),
        // new NoEscapeHandler());
        if (schema != null) {
            marshaller.setSchema(schema);
        }

        return marshaller;
    }

    /**
     * Realiza a operacao de conversao Object -> XML.
     * 
     * @param schema
     * @param obj
     * @param xmlFileOutput
     * @throws JAXBException
     */
    public static void parseToXML(Schema schema, Object obj, File xmlFileOutput) throws JAXBException {

        Marshaller marshaller = createMarshaller(obj.getClass(), schema, true);
        marshaller.marshal(obj, xmlFileOutput);
    }

    /**
     * Realiza a operacao de conversao Object -> XML.
     * 
     * @param obj
     * @param xmlFileOutput
     * @throws JAXBException
     */
    public static void parseToXML(Object obj, File xmlFileOutput) throws JAXBException {

        Marshaller marshaller = createMarshaller(obj.getClass(), null, true);
        marshaller.marshal(obj, xmlFileOutput);
    }

    /**
     * Realiza a operacao de conversao Object -> XML.
     * 
     * @param schema
     * @param obj
     * @return {@link String}
     * @throws JAXBException
     */
    public static String parseToXML(Schema schema, Object obj) throws JAXBException {

        Marshaller marshaller = createMarshaller(obj.getClass(), schema, true);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(obj, baos);

        return new String(baos.toByteArray(), Charset.defaultCharset());
    }

    /**
     * Realiza a operacao de conversao Object -> XML.
     * 
     * @param obj
     * @return {@link String}
     * @throws JAXBException
     */
    public static String parseToXML(Object obj) throws JAXBException {

        return parseToXML(obj, false);
    }

    /**
     * Realiza a operacao de conversao Object -> XML.
     * 
     * @param obj
     * @param removeXmlDeclaration
     * @return {@link String}
     * @throws JAXBException
     */
    public static String parseToXML(Object obj, boolean removeXmlDeclaration) throws JAXBException {

        return parseToXML(obj, removeXmlDeclaration, true);
    }

    /**
     * Realiza a operacao de conversao Object -> XML.
     * 
     * @param obj
     * @param removeXmlDeclaration
     * @param formatXML
     * @return {@link String}
     * @throws JAXBException
     */
    public static String parseToXML(Object obj, boolean removeXmlDeclaration, boolean formatXML) throws JAXBException {

        Marshaller marshaller = createMarshaller(obj.getClass(), null, formatXML);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(obj, baos);

        String xml = new String(baos.toByteArray(), Charset.defaultCharset());
        if (StringUtils.contains(xml, "<?xml")) {
            xml = StringUtils.substringAfter(xml, "?>");
        }
        return xml;
    }

}
