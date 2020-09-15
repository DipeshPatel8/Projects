﻿#pragma checksum "..\..\ExpenseForm.xaml" "{ff1816ec-aa5e-4d10-87f7-6f4963833460}" "08A0B88BDF0BD09BBA98C46142A758448717204D"
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.42000
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

using Budget;
using System;
using System.Diagnostics;
using System.Windows;
using System.Windows.Automation;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Media.Effects;
using System.Windows.Media.Imaging;
using System.Windows.Media.Media3D;
using System.Windows.Media.TextFormatting;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Shell;


namespace Budget {
    
    
    /// <summary>
    /// ExpenseForm
    /// </summary>
    public partial class ExpenseForm : System.Windows.Window, System.Windows.Markup.IComponentConnector {
        
        
        #line 9 "..\..\ExpenseForm.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.StackPanel Window;
        
        #line default
        #line hidden
        
        
        #line 11 "..\..\ExpenseForm.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.TextBlock txtMainTitle;
        
        #line default
        #line hidden
        
        
        #line 20 "..\..\ExpenseForm.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.ComboBox cmbCategoryList;
        
        #line default
        #line hidden
        
        
        #line 21 "..\..\ExpenseForm.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.TextBlock txtCategoryInvalid;
        
        #line default
        #line hidden
        
        
        #line 31 "..\..\ExpenseForm.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.DatePicker txtDate;
        
        #line default
        #line hidden
        
        
        #line 32 "..\..\ExpenseForm.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.TextBlock txtDateInvalid;
        
        #line default
        #line hidden
        
        
        #line 43 "..\..\ExpenseForm.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.TextBox txtDescription;
        
        #line default
        #line hidden
        
        
        #line 54 "..\..\ExpenseForm.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.TextBox txtAmount;
        
        #line default
        #line hidden
        
        
        #line 55 "..\..\ExpenseForm.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.TextBlock txtAmountInvalid;
        
        #line default
        #line hidden
        
        
        #line 59 "..\..\ExpenseForm.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.CheckBox cbCredit;
        
        #line default
        #line hidden
        
        
        #line 68 "..\..\ExpenseForm.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button btnModify;
        
        #line default
        #line hidden
        
        
        #line 69 "..\..\ExpenseForm.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button btnCancel2;
        
        #line default
        #line hidden
        
        
        #line 70 "..\..\ExpenseForm.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button btnDelete;
        
        #line default
        #line hidden
        
        
        #line 80 "..\..\ExpenseForm.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button btnAdd;
        
        #line default
        #line hidden
        
        
        #line 81 "..\..\ExpenseForm.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button btnCancel;
        
        #line default
        #line hidden
        
        
        #line 82 "..\..\ExpenseForm.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.Button btnClose;
        
        #line default
        #line hidden
        
        
        #line 88 "..\..\ExpenseForm.xaml"
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1823:AvoidUnusedPrivateFields")]
        internal System.Windows.Controls.TextBlock txtLastAction;
        
        #line default
        #line hidden
        
        private bool _contentLoaded;
        
        /// <summary>
        /// InitializeComponent
        /// </summary>
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [System.CodeDom.Compiler.GeneratedCodeAttribute("PresentationBuildTasks", "4.0.0.0")]
        public void InitializeComponent() {
            if (_contentLoaded) {
                return;
            }
            _contentLoaded = true;
            System.Uri resourceLocater = new System.Uri("/Budget;component/expenseform.xaml", System.UriKind.Relative);
            
            #line 1 "..\..\ExpenseForm.xaml"
            System.Windows.Application.LoadComponent(this, resourceLocater);
            
            #line default
            #line hidden
        }
        
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [System.CodeDom.Compiler.GeneratedCodeAttribute("PresentationBuildTasks", "4.0.0.0")]
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Never)]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Design", "CA1033:InterfaceMethodsShouldBeCallableByChildTypes")]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Maintainability", "CA1502:AvoidExcessiveComplexity")]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1800:DoNotCastUnnecessarily")]
        void System.Windows.Markup.IComponentConnector.Connect(int connectionId, object target) {
            switch (connectionId)
            {
            case 1:
            this.Window = ((System.Windows.Controls.StackPanel)(target));
            return;
            case 2:
            this.txtMainTitle = ((System.Windows.Controls.TextBlock)(target));
            return;
            case 3:
            this.cmbCategoryList = ((System.Windows.Controls.ComboBox)(target));
            return;
            case 4:
            this.txtCategoryInvalid = ((System.Windows.Controls.TextBlock)(target));
            return;
            case 5:
            this.txtDate = ((System.Windows.Controls.DatePicker)(target));
            return;
            case 6:
            this.txtDateInvalid = ((System.Windows.Controls.TextBlock)(target));
            return;
            case 7:
            this.txtDescription = ((System.Windows.Controls.TextBox)(target));
            return;
            case 8:
            this.txtAmount = ((System.Windows.Controls.TextBox)(target));
            
            #line 54 "..\..\ExpenseForm.xaml"
            this.txtAmount.PreviewTextInput += new System.Windows.Input.TextCompositionEventHandler(this.NumberPreviewTextInput);
            
            #line default
            #line hidden
            return;
            case 9:
            this.txtAmountInvalid = ((System.Windows.Controls.TextBlock)(target));
            return;
            case 10:
            this.cbCredit = ((System.Windows.Controls.CheckBox)(target));
            return;
            case 11:
            this.btnModify = ((System.Windows.Controls.Button)(target));
            
            #line 68 "..\..\ExpenseForm.xaml"
            this.btnModify.Click += new System.Windows.RoutedEventHandler(this.ModifyExpense_Click);
            
            #line default
            #line hidden
            return;
            case 12:
            this.btnCancel2 = ((System.Windows.Controls.Button)(target));
            
            #line 69 "..\..\ExpenseForm.xaml"
            this.btnCancel2.Click += new System.Windows.RoutedEventHandler(this.CancelExpense_Click);
            
            #line default
            #line hidden
            return;
            case 13:
            this.btnDelete = ((System.Windows.Controls.Button)(target));
            
            #line 70 "..\..\ExpenseForm.xaml"
            this.btnDelete.Click += new System.Windows.RoutedEventHandler(this.DeleteExpense_Click);
            
            #line default
            #line hidden
            return;
            case 14:
            this.btnAdd = ((System.Windows.Controls.Button)(target));
            
            #line 80 "..\..\ExpenseForm.xaml"
            this.btnAdd.Click += new System.Windows.RoutedEventHandler(this.SaveExpense_Click);
            
            #line default
            #line hidden
            return;
            case 15:
            this.btnCancel = ((System.Windows.Controls.Button)(target));
            
            #line 81 "..\..\ExpenseForm.xaml"
            this.btnCancel.Click += new System.Windows.RoutedEventHandler(this.CancelExpense_Click);
            
            #line default
            #line hidden
            return;
            case 16:
            this.btnClose = ((System.Windows.Controls.Button)(target));
            
            #line 82 "..\..\ExpenseForm.xaml"
            this.btnClose.Click += new System.Windows.RoutedEventHandler(this.Close_Click);
            
            #line default
            #line hidden
            return;
            case 17:
            this.txtLastAction = ((System.Windows.Controls.TextBlock)(target));
            return;
            }
            this._contentLoaded = true;
        }
    }
}

