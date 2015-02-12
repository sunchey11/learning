1.require函数的执行步骤
module.js中的代码
Module.prototype.require = function(path) {
  assert(typeof path === 'string', 'path must be a string');
  assert(path, 'missing path');
  return Module._load(path, this);
};
