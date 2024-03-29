﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

// ============================================================================
// (c) Sandy Bultena 2018
// * Released under the GNU General Public License
// ============================================================================

namespace Budget
{
    // ====================================================================
    // CLASS: Expense
    //        - An individual expens for budget program
    // ====================================================================
    public class Expense
    {
        // ====================================================================
        // Properties
        // ====================================================================
        public int Id { get; }
        public DateTime Date { get; set; }
        public Double Amount { get; set; }
        public String Description { get; set; }
        public int Category { get; set; }

        // ====================================================================
        // Constructor
        //    NB: there is no verification the expense category exists in the
        //        categories object
        // ====================================================================
        public Expense(int id, DateTime date, int category, Double amount, String description)
        {
            this.Id = id;
            this.Date = date;
            this.Amount = amount;
            this.Description = description;
            this.Category = category;
        }       
    }
}
