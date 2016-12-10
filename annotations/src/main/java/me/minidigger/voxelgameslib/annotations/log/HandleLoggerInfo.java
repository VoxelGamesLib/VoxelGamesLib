package me.minidigger.voxelgameslib.annotations.log;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCMethodInvocation;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;

import org.kohsuke.MetaInfServices;

import lombok.core.AnnotationValues;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;
import lombok.javac.JavacTreeMaker;

import static lombok.javac.handlers.JavacHandlerUtil.MemberExistsResult;
import static lombok.javac.handlers.JavacHandlerUtil.chainDotsString;
import static lombok.javac.handlers.JavacHandlerUtil.fieldExists;
import static lombok.javac.handlers.JavacHandlerUtil.injectFieldSuppressWarnings;
import static lombok.javac.handlers.JavacHandlerUtil.recursiveSetGeneratedBy;

/**
 * Created by Martin on 10.12.2016.
 */
@MetaInfServices(JavacAnnotationHandler.class)
public class HandleLoggerInfo extends JavacAnnotationHandler<LoggerInfo> {

    @Override
    public void handle(AnnotationValues<LoggerInfo> annotation, JCTree.JCAnnotation ast, JavacNode annotationNode) {
        System.out.println("handle stuff?!");
        processAnnotation(annotation, annotationNode);
    }

    private void processAnnotation(AnnotationValues<?> annotation, JavacNode annotationNode) {
        JavacNode typeNode = annotationNode.up();
        switch (typeNode.getKind()) {
            case TYPE:
                String logFieldName = "log";
                if ((((JCClassDecl) typeNode.get()).mods.flags & Flags.INTERFACE) != 0) {
                    annotationNode.addError("@LoggerInfo is legal only on classes and enums.");
                    return;
                }
                if (fieldExists(logFieldName, typeNode) != MemberExistsResult.NOT_EXISTS) {
                    annotationNode.addWarning("Field '" + logFieldName + "' already exists.");
                    return;
                }
                createField(typeNode, annotation, annotationNode.get(), logFieldName);
                break;
            default:
                annotationNode.addError("@LoggerInfo is legal only on types.");
                break;
        }
    }

    private JCTree.JCFieldAccess selfType(JavacNode typeNode) {
        JavacTreeMaker maker = typeNode.getTreeMaker();
        Name name = ((JCClassDecl) typeNode.get()).name;
        return maker.Select(maker.Ident(name), typeNode.toName("class"));
    }

    private boolean createField(JavacNode typeNode, AnnotationValues<?> annotation, JCTree source, String logFieldName) {
        JavacTreeMaker maker = typeNode.getTreeMaker();

        // private static final me.minidigger.voxelgameslib.api.log.Logger log = me.minidigger.voxelgameslib.api.log.Logger.getLogger(TargetType.class.getName());
        JCExpression loggerType = chainDotsString(typeNode, "me.minidigger.voxelgameslib.api.log.Logger");
        JCExpression factoryMethod = chainDotsString(typeNode, "me.minidigger.voxelgameslib.api.log.Logger.getLogger");
        JCExpression method = maker.Select(selfType(typeNode), typeNode.toName("getName"));
        JCExpression loggerName = maker.Apply(List.nil(), method, List.nil());

        JCMethodInvocation factoryMethodCall = maker.Apply(List.nil(), factoryMethod, List.of(loggerName));

        JCVariableDecl fieldDecl = recursiveSetGeneratedBy(maker.VarDef(
                maker.Modifiers(Flags.PRIVATE | Flags.FINAL | Flags.STATIC),
                typeNode.toName(logFieldName), loggerType, factoryMethodCall), source, typeNode.getContext());

        injectFieldSuppressWarnings(typeNode, fieldDecl);
        return true;
    }
}
