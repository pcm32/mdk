package uk.ac.ebi.mdk.io;


import uk.ac.ebi.caf.utility.version.Version;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * AbstractDataInput - 12.03.2012 <br/>
 * <p/>
 * Class descriptions.
 *
 * @author johnmay
 * @author $Author$ (this version)
 * @version $Rev$
 */
public class AbstractDataInput<M>
        extends MarshalManager<M> {

    private DataInput in;
    private Map<Integer,Object> objectMap = new HashMap<Integer,Object>(300);
    
    public AbstractDataInput(DataInput in, Version version){
        super(version);
        this.in = in;
    }

    public DataInput getDataInput(){
        return in;
    }

    public Class readClass() throws IOException, ClassNotFoundException {
        Integer id = readObjectId();
        return hasObject(id) ? (Class) get(id) : put(id, readNewClass());
    }
    
    private Class readNewClass() throws IOException, ClassNotFoundException {
        String name = in.readUTF();
        return  Class.forName(name);
    }
    
    public Integer readObjectId() throws IOException {
        return in.readInt();
    }
    
    public boolean hasObject(Integer id){
        return objectMap.containsKey(id);
    }
    
    public <O> O get(Integer id){
        return (O) objectMap.get(id);
    }
    
    public <O> O put(Integer id, O obj){
        objectMap.put(id, obj);
        return obj;
    }

}
