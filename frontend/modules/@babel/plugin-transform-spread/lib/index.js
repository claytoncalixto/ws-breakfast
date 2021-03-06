"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = void 0;

var _helperPluginUtils = require("@babel/helper-plugin-utils");

var _helperSkipTransparentExpressionWrappers = require("@babel/helper-skip-transparent-expression-wrappers");

var _core = require("@babel/core");

var _default = (0, _helperPluginUtils.declare)((api, options) => {
  api.assertVersion(7);
  const {
    loose,
    allowArrayLike
  } = options;

  function getSpreadLiteral(spread, scope) {
    if (loose && !_core.types.isIdentifier(spread.argument, {
      name: "arguments"
    })) {
      return spread.argument;
    } else {
      return scope.toArray(spread.argument, true, allowArrayLike);
    }
  }

  function hasSpread(nodes) {
    for (let i = 0; i < nodes.length; i++) {
      if (_core.types.isSpreadElement(nodes[i])) {
        return true;
      }
    }

    return false;
  }

  function push(_props, nodes) {
    if (!_props.length) return _props;
    nodes.push(_core.types.arrayExpression(_props));
    return [];
  }

  function build(props, scope) {
    const nodes = [];
    let _props = [];

    for (const prop of props) {
      if (_core.types.isSpreadElement(prop)) {
        _props = push(_props, nodes);
        nodes.push(getSpreadLiteral(prop, scope));
      } else {
        _props.push(prop);
      }
    }

    push(_props, nodes);
    return nodes;
  }

  return {
    name: "transform-spread",
    visitor: {
      ArrayExpression(path) {
        const {
          node,
          scope
        } = path;
        const elements = node.elements;
        if (!hasSpread(elements)) return;
        const nodes = build(elements, scope);
        let first = nodes[0];

        if (nodes.length === 1 && first !== elements[0].argument) {
          path.replaceWith(first);
          return;
        }

        if (!_core.types.isArrayExpression(first)) {
          first = _core.types.arrayExpression([]);
        } else {
          nodes.shift();
        }

        path.replaceWith(_core.types.callExpression(_core.types.memberExpression(first, _core.types.identifier("concat")), nodes));
      },

      CallExpression(path) {
        const {
          node,
          scope
        } = path;
        const args = node.arguments;
        if (!hasSpread(args)) return;
        const calleePath = (0, _helperSkipTransparentExpressionWrappers.skipTransparentExprWrappers)(path.get("callee"));

        if (calleePath.isSuper()) {
          throw path.buildCodeFrameError("It's not possible to compile spread arguments in `super()` without compiling classes.\n" + "Please add '@babel/plugin-transform-classes' to your Babel configuration.");
        }

        let contextLiteral = scope.buildUndefinedNode();
        node.arguments = [];
        let nodes;

        if (args.length === 1 && args[0].argument.name === "arguments") {
          nodes = [args[0].argument];
        } else {
          nodes = build(args, scope);
        }

        const first = nodes.shift();

        if (nodes.length) {
          node.arguments.push(_core.types.callExpression(_core.types.memberExpression(first, _core.types.identifier("concat")), nodes));
        } else {
          node.arguments.push(first);
        }

        const callee = calleePath.node;

        if (calleePath.isMemberExpression()) {
          const temp = scope.maybeGenerateMemoised(callee.object);

          if (temp) {
            callee.object = _core.types.assignmentExpression("=", temp, callee.object);
            contextLiteral = temp;
          } else {
            contextLiteral = _core.types.cloneNode(callee.object);
          }
        }

        node.callee = _core.types.memberExpression(node.callee, _core.types.identifier("apply"));

        if (_core.types.isSuper(contextLiteral)) {
          contextLiteral = _core.types.thisExpression();
        }

        node.arguments.unshift(_core.types.cloneNode(contextLiteral));
      },

      NewExpression(path) {
        const {
          node,
          scope
        } = path;
        let args = node.arguments;
        if (!hasSpread(args)) return;
        const nodes = build(args, scope);
        const first = nodes.shift();

        if (nodes.length) {
          args = _core.types.callExpression(_core.types.memberExpression(first, _core.types.identifier("concat")), nodes);
        } else {
          args = first;
        }

        path.replaceWith(_core.types.callExpression(path.hub.addHelper("construct"), [node.callee, args]));
      }

    }
  };
});

exports.default = _default;