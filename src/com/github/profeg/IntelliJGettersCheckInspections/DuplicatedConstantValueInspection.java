package com.github.profeg.IntelliJGettersCheckInspections;

import com.intellij.psi.PsiField;
import com.intellij.psi.PsiModifier;
import com.siyeh.ig.BaseInspection;
import com.siyeh.ig.BaseInspectionVisitor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.intellij.psi.PsiModifier.FINAL;
import static com.intellij.psi.PsiModifier.STATIC;

public class DuplicatedConstantValueInspection extends BaseInspection {
    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Duplicated constant value";
    }

    @NotNull
    @Override
    protected String buildErrorString(Object... infos) {
        return (infos[0].toString());
    }

    @Override
    public BaseInspectionVisitor buildVisitor() {
        return new DuplicatedConstantValueInspectionVisitor();
    }

    private class DuplicatedConstantValueInspectionVisitor extends BaseInspectionVisitor {
        private Set constantValues = new HashSet();

        @Override
        public void visitField(PsiField field) {
            super.visitField(field);
            if (isConstant(field) && field.getInitializer() != null) {
                String fieldValue = field.getInitializer().getText();
                if (!constantValues.add(fieldValue)) {
                    registerError(field.getNameIdentifier(), "Duplicated constant value");
                }
            }
        }

        private boolean isConstant(PsiField field) {
            return field.getModifierList().hasModifierProperty(STATIC) && field.getModifierList().hasModifierProperty(FINAL);
        }
    }
}
