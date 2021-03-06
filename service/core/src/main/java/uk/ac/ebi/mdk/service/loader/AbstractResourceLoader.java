package uk.ac.ebi.mdk.service.loader;

import org.apache.log4j.Logger;
import uk.ac.ebi.mdk.service.loader.location.DefaultLocationDescription;
import uk.ac.ebi.mdk.service.ResourceLoader;
import uk.ac.ebi.mdk.service.exception.MissingLocationException;
import uk.ac.ebi.mdk.service.location.LocationDescription;
import uk.ac.ebi.mdk.service.location.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * AbstractResourceLoader - 23.02.2012 <br/>
 * <p/>
 * Class descriptions.
 *
 * @author johnmay
 * @author $Author: johnmay $ (this version)
 * @version $Rev: 1719 $
 */
public abstract class AbstractResourceLoader
        implements ResourceLoader {

    private static final Logger LOGGER = Logger.getLogger(AbstractResourceLoader.class);
    private Map<String, ResourceLocation> locationMap = new HashMap<String, ResourceLocation>(6);
    private boolean cancelled = false;

    private Map<String, LocationDescription> requiredResources = new HashMap<String, LocationDescription>();


    public <T extends ResourceLocation> void addRequiredResource(String name,
                                                                 String description,
                                                                 Class<T> c,
                                                                 T defaultLocation) {
        addRequiredResource(new DefaultLocationDescription(name,
                                                           description,
                                                           c,
                                                           defaultLocation));
    }

    public <T extends ResourceLocation> void addRequiredResource(String name,
                                                                 String description,
                                                                 Class<T> c) {
        addRequiredResource(new DefaultLocationDescription(name,
                                                           description,
                                                           c
        ));
    }

    /**
     * Add a required resource location to the loader.
     *
     * @param resource
     */
    public void addRequiredResource(LocationDescription resource) {

        requiredResources.put(resource.getKey(), resource);

        // if the resource has a default location, add it to the location map
        if (resource.hasDefault()) {
            locationMap.put(resource.getKey(), resource.getDefault());
        }

    }


    /**
     * @inheritDoc
     */
    @Override
    public void addLocation(String key, ResourceLocation location) {
        locationMap.put(key, location);
    }

    @Override
    public void removeLocation(String key) {
        locationMap.remove(key);
    }

    public boolean hasLocation(String key){
        return locationMap.containsKey(key) && locationMap.get(key).isAvailable();
    }

    /**
     * @inheritDoc
     */
    @Override
    public <T extends ResourceLocation> T getLocation(String key) throws MissingLocationException {
        if (locationMap.containsKey(key)) {
            return (T) locationMap.get(key);
        }
        key = DefaultLocationDescription.createKey(key);
        if (locationMap.containsKey(key)) {
            return (T) locationMap.get(key);
        }
        throw new MissingLocationException("Could not find location for key " + key);
    }


    /**
     * @inheritDoc
     */
    @Override
    public boolean canUpdate() {
        for (String key : getRequiredResources().keySet()) {
            if (!locationMap.containsKey(key)
                    || !locationMap.get(key).isAvailable()) {
                return false;
            }
        }
        return true;
    }


    /**
     * @inheritDoc
     */
    @Override
    public Map<String, LocationDescription> getRequiredResources() {
        return requiredResources;
    }

    /**
     * @inheritDoc
     */
    @Override
    public synchronized void cancel() {
        cancelled = true;
    }

    /**
     * Determine whether the update has been cancelled
     * @return
     */
    public synchronized boolean isCancelled(){
        return cancelled;
    }

    /**
     * Reset the cancelled state (should be called before update)
     */
    @Override
    public void uncancel(){
        cancelled = false;
    }

}
