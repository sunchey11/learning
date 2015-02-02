package interceptor;

import java.lang.reflect.Method;

import org.mozilla.javascript.Callable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Delegator;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeFunction;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class ScriptableObjectDelegator extends ScriptableObject {
	private ScriptableObject instance;

	public int hashCode() {
		return instance.hashCode();
	}

	public boolean equals(Object obj) {
		return instance.equals(obj);
	}

	public String toString() {
		return instance.toString();
	}

	public String getTypeOf() {
		return instance.getTypeOf();
	}

	public boolean has(String name, Scriptable start) {
		return instance.has(name, start);
	}

	public boolean has(int index, Scriptable start) {
		return instance.has(index, start);
	}

	public Object get(String name, Scriptable start) {
		Object object = instance.get(name, start);
		if(object instanceof Function){
			Function f = (Function) object;
			return new Delegator(f){
				@Override
				public Object call(Context cx, Scriptable scope,
						Scriptable thisObj, Object[] args) {
					NativeFunction tf = (NativeFunction) this.obj;
					String functionName = tf.getFunctionName();
					System.out.println("start "+functionName);

					Object r = super.call(cx, scope, thisObj, args);
					System.out.println("end");
					return r;
					
				}
				@Override
				public Object get(String name, Scriptable start) {
				
					return super.get(name, start);
				}
			};
		}else if(object instanceof ScriptableObject){
			return new ScriptableObjectDelegator((ScriptableObject) object);
		}
		return object;
	}

	public Object get(int index, Scriptable start) {
		Object object = instance.get(index, start);
		return object;
	}

	public void put(String name, Scriptable start, Object value) {
		instance.put(name, start, value);
	}

	public void put(int index, Scriptable start, Object value) {
		instance.put(index, start, value);
	}

	public void delete(String name) {
		instance.delete(name);
	}

	public void delete(int index) {
		instance.delete(index);
	}

	public void putConst(String name, Scriptable start, Object value) {
		instance.putConst(name, start, value);
	}

	public void defineConst(String name, Scriptable start) {
		instance.defineConst(name, start);
	}

	public boolean isConst(String name) {
		return instance.isConst(name);
	}

	public void setAttributes(int index, Scriptable start, int attributes) {
		instance.setAttributes(index, start, attributes);
	}

	public int getAttributes(String name) {
		return instance.getAttributes(name);
	}

	public int getAttributes(int index) {
		return instance.getAttributes(index);
	}

	public void setAttributes(String name, int attributes) {
		instance.setAttributes(name, attributes);
	}

	public void setAttributes(int index, int attributes) {
		instance.setAttributes(index, attributes);
	}

	public void setGetterOrSetter(String name, int index,
			Callable getterOrSetter, boolean isSetter) {
		instance.setGetterOrSetter(name, index, getterOrSetter, isSetter);
	}

	public Object getGetterOrSetter(String name, int index, boolean isSetter) {
		return instance.getGetterOrSetter(name, index, isSetter);
	}

	public Scriptable getPrototype() {
		return instance.getPrototype();
	}

	public void setPrototype(Scriptable m) {
		instance.setPrototype(m);
	}

	public Scriptable getParentScope() {
		return instance.getParentScope();
	}

	public void setParentScope(Scriptable m) {
		instance.setParentScope(m);
	}

	public Object[] getIds() {
		return instance.getIds();
	}

	public Object[] getAllIds() {
		return instance.getAllIds();
	}

	public Object getDefaultValue(Class<?> typeHint) {
		return instance.getDefaultValue(typeHint);
	}

	public boolean hasInstance(Scriptable instance) {
		return instance.hasInstance(instance);
	}

	public boolean avoidObjectDetection() {
		return instance.avoidObjectDetection();
	}

	public void defineProperty(String propertyName, Object value, int attributes) {
		instance.defineProperty(propertyName, value, attributes);
	}

	public void defineProperty(String propertyName, Class<?> clazz,
			int attributes) {
		instance.defineProperty(propertyName, clazz, attributes);
	}

	public void defineProperty(String propertyName, Object delegateTo,
			Method getter, Method setter, int attributes) {
		instance.defineProperty(propertyName, delegateTo, getter, setter,
				attributes);
	}

	public void defineOwnProperties(Context cx, ScriptableObject props) {
		instance.defineOwnProperties(cx, props);
	}

	public void defineOwnProperty(Context cx, Object id, ScriptableObject desc) {
		instance.defineOwnProperty(cx, id, desc);
	}

	public void defineFunctionProperties(String[] names, Class<?> clazz,
			int attributes) {
		instance.defineFunctionProperties(names, clazz, attributes);
	}

	public boolean isExtensible() {
		return instance.isExtensible();
	}

	public void preventExtensions() {
		instance.preventExtensions();
	}

	public void sealObject() {
		instance.sealObject();
	}

	public int size() {
		return instance.size();
	}

	public boolean isEmpty() {
		return instance.isEmpty();
	}

	public Object get(Object key) {
		return instance.get(key);
	}

	public ScriptableObjectDelegator(ScriptableObject instance) {
		this.instance = instance;
	}

	@Override
	public String getClassName() {
		return instance.getClassName();
	}


}
