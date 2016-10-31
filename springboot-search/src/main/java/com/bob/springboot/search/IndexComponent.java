package com.bob.springboot.search;


import com.bob.springboot.search.model.SearchField;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


/**
 * Created by Bob Jiang on 2016/10/27.
 */
public enum IndexComponent {

    INSTANCE;

    private ClientComponent clientComponent = ClientComponent.INSTANCE;

    public boolean createIndex(String indexName) {
        try {
            CreateIndex cIndex = new CreateIndex.Builder(indexName).build();
            JestResult result = clientComponent.execute(cIndex);
            return (result != null && result.isSucceeded()) ? true : false;
        } catch (Exception e) {
            throw new RuntimeException("createIndex failed! " + e.getMessage(), e);
        }
    }

    public boolean existsIndex(String indexName) {
        try {
            IndicesExists indicesExists = new IndicesExists.Builder(indexName).build();
            JestResult result = clientComponent.execute(indicesExists);
            return (result != null && result.isSucceeded()) ? true : false;
        } catch (Exception e) {
            throw new RuntimeException("existsIndex failed! " + e.getMessage(), e);
        }
    }

    public boolean deleteIndex(String indexName) {
        try {
            DeleteIndex index = new DeleteIndex.Builder(indexName).build();
            JestResult result = clientComponent.execute(index);
            return (result != null && result.isSucceeded()) ? true : false;
        } catch (Exception e) {
            throw new RuntimeException("deleteIndex failed! " + e.getMessage(), e);
        }
    }

    public boolean saveDoc(String indexName, String indexType, String indexId, Object data) {
        try {
            Bulk.Builder bulkBuilder = new Bulk.Builder();
            Index index = new Index.Builder(data).index(indexName).id(indexId).type(indexType).build();
            bulkBuilder.addAction(index);
            JestResult result = clientComponent.execute(bulkBuilder.build());
            return (result != null && result.isSucceeded()) ? true : false;
        } catch (Exception e) {
            throw new RuntimeException("saveDoc failed! " + e.getMessage(), e);
        }
    }

    public <T> void saveDocList(String indexName, String indexType, List<T> dataList) {
        saveDocList(indexName, indexType, dataList, "id");
    }

    public <T> void saveDocList(String indexName, String indexType, List<T> dataList, String indexIdField) {
        if (dataList == null || dataList.size() == 0)
            throw new RuntimeException("dataList is null");
        if (!existsIndex(indexName))
            createIndex(indexName);
        for (T t : dataList) {
            saveDoc(indexName, indexType, getFieldValue(t, indexIdField), t);
        }
    }

    public boolean deleteDoc(String indexName, String indexType, String indexId) {
        try {
            Bulk.Builder bulkBuilder = new Bulk.Builder();
            Delete delete = new Delete.Builder(indexId).index(indexName).type(indexType).build();
            bulkBuilder.addAction(delete);
            JestResult result = clientComponent.execute(bulkBuilder.build());
            return (result != null && result.isSucceeded()) ? true : false;
        } catch (Exception e) {
            throw new RuntimeException("deleteDoc failed! " + e.getMessage(), e);
        }
    }

    private String getFieldValue(Object obj, String fieldName) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
            return pd.getReadMethod().invoke(obj).toString();
        } catch (Exception e) {
            throw new RuntimeException("fieldName[" + fieldName + "] is wrong", e);
        }
    }

}
