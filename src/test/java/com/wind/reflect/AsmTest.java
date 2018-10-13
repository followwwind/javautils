package com.wind.reflect;

import com.wind.AAA;
import org.junit.Test;
import org.objectweb.asm.*;

import java.io.IOException;

/**
 * @Title: AsmTest
 * @Package com.wind.reflect
 * @Description: TODO
 * @author wind
 * @date 2018/10/13 9:29
 * @version V1.0
 */
public class AsmTest {

    @Test
    public void read() throws IOException {
        ClassReader classReader = new ClassReader("com.wind.AAA");
        classReader.accept(new ClassVisitor(){

            public void visit(int version, int access, String name,
                String signature, String superName, String[] interfaces) {
                System.out.println("class name:" + name);
                System.out.println("super class name:" + superName);
                System.out.println("class version:" + version);
                System.out.println("class access:" + access);
                System.out.println("class signature:" + signature);
                if(interfaces != null && interfaces.length > 0){
                    for(String str : interfaces){
                        System.out.println("implemented interface name:" + str);
                    }
                }

            }

            public void visitSource(String source, String debug) {

            }

            public void visitOuterClass(String owner, String name, String desc) {

            }

            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                System.out.println(desc);
                return null;
            }

            public void visitAttribute(Attribute attribute) {

            }

            public void visitInnerClass(String name, String outerName, String innerName, int access) {

            }

            public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
                System.out.println(name + "-" + desc + "-" + signature + "-" + value);
                return null;
            }

            public MethodVisitor visitMethod(int access, String name, String desc,
                 String signature, String[] exceptions) {
//                System.out.println(name);
                return null;
            }

            public void visitEnd() {
                System.out.println("-------------end------------");
            }
        }, 0);
    }

    @Test
    public void write() throws IOException {
        AAA a = new AAA();
        a.setCode(1);
        ClassReader cr = new ClassReader(AAA.class.getName());
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cr.accept(new ClassAdapter(cw){

            @Override
            public void visit(final int version, final int access, final String name,
                final String signature, final String superName, final String[] interfaces) {

            }

            @Override
            public MethodVisitor visitMethod(final int access, final String name,
                final String desc, final String signature, final String[] exceptions) {
                     if("setCode".equals(name)){
                         //这里只是简单的比较了方法名字，其实还需要比较方法参数，参数信息在desc中
                         return cv.visitMethod(access, name + "$1", desc, signature, exceptions);
                     }

                     return cv.visitMethod(access, name, desc, signature, exceptions);
            }
        }, 0);
        //到这里，如果调用cr.toByteArray，生成的字节码中，已经没有execute方法了，而是execute$1

        //我们接着需要增加一个execute方法
        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "setCode",
                "()V", null,
                null);
        //开始增加代码
        mv.visitCode();
        //接下来，我们需要把新的execute方法的内容，增加到这个方法中
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("Before execute");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "org/vanadies/bytecode/example/asm3/ClassWriterAopExample$Foo$1", "execute$1", "()V");
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("End execute");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
        mv.visitInsn(Opcodes.RETURN);
        //这个地方，最大的操作数栈和最大的本地变量的空间，是自动计算的，是因为构造ClassWriter的时候使用了ClassWriter.COMPUTE_MAXS
        mv.visitMaxs(0, 0);
        mv.visitEnd();
        //到这里，就完成了execute方法的添加。
        //可以把字节码写入到文件，用javap -c，来看下具体的内容

    }


}
